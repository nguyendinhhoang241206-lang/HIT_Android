# Tìm hiểu về Repository Pattern và Room Database trong Android

Repository Pattern và Room Database là bộ đôi phổ biến giúp quản lý dữ liệu hiệu quả, tách biệt giao diện người dùng (UI) khỏi cơ sở dữ liệu (SQLite).

---

## 1. Sơ đồ kiến trúc

Dưới đây là mô hình hoạt động đơn giản của hệ thống:

![Kiến trúc Repository & Room]
![alt text](image-1.png)

- **Activity (Giao diện):** Nơi hiển thị dữ liệu và nhận thao tác từ người dùng.
- **Repository (Lớp trung gian):** Nơi xử lý logic dữ liệu (lấy từ đâu, lưu thế nào). Activity sẽ gọi Repository khi cần dữ liệu.
- **Room Database:** Lớp quản lý cơ sở dữ liệu SQLite dưới máy, bao gồm:
  - **Entity:** Định nghĩa cấu trúc bảng dữ liệu.
  - **DAO (Data Access Object):** Định nghĩa các câu lệnh truy vấn dữ liệu (Insert, Update, Delete, Select).

---

## 2. Cấu hình Gradle (`build.gradle`)

Để sử dụng được Room Database và Repository, bạn cần cấu hình các file Gradle như sau:

### Lớp 1: Project-level `build.gradle.kts` (File gốc ngoài cùng)

Thêm plugin **KSP** (Kotlin Symbol Processing) để Room có thể tự sinh code biên dịch:

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    // Thêm plugin KSP tương thích với phiên bản Kotlin của bạn (ví dụ: Kotlin 1.9.24)
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}
```

### Lớp 2: App-level `app/build.gradle.kts` (File trong thư mục app)

Áp dụng plugin KSP và thêm các thư viện cần thiết cho Room và Coroutines:

```kotlin
plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp") // Áp dụng plugin KSP
}

dependencies {
    // ... các thư viện mặc định khác ...

    // Thư viện Room Database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion") // Hỗ trợ Coroutines (suspend) và Flow
    ksp("androidx.room:room-compiler:$roomVersion") // Trình biên dịch sinh code cho Room

    // Kotlin Coroutines (Để chạy tác vụ Database dưới Background Thread)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

---

## 3. Code mẫu tối giản

### Bước 1: Tạo Entity (Bảng dữ liệu)

Định nghĩa một bảng `user` chứa thông tin người dùng:

```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int
)
```

### Bước 2: Tạo DAO (Các câu lệnh truy vấn)

Định nghĩa các hàm thêm và đọc dữ liệu. Sử dụng `suspend` để chạy ngầm (Background Thread).

```kotlin
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>
}
```

### Bước 3: Tạo Room Database

Lớp quản lý kết nối và tạo cơ sở dữ liệu (sử dụng Singleton Pattern):

```kotlin
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

### Bước 4: Tạo Repository (Lớp trung gian)

Cầu nối giữa database và giao diện:

```kotlin
class UserRepository(private val userDao: UserDao) {
    suspend fun addUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}
```

### Bước 5: Gọi sử dụng trong Activity

Sử dụng `lifecycleScope.launch` để gọi các hàm bất đồng bộ từ Repository trên Main Thread một cách an toàn:

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Database và Repository
        val database = AppDatabase.getDatabase(this)
        repository = UserRepository(database.userDao())

        // Thêm và lấy dữ liệu bằng Coroutines (chạy ngầm)
        lifecycleScope.launch {
            repository.addUser(User(name = "Hoàng", age = 20))
            val userList = repository.getAllUsers()
            // Hiển thị danh sách userList lên UI tại đây...
        }
    }
}
```
