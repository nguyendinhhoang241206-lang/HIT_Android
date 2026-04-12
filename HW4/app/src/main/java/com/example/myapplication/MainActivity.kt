package com.example.myapplication

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        personAdapter = PersonAdapter(
            onItemClick = { person: Person ->
                Toast.makeText(this, "Tên: ${person.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { person: Person ->
                Toast.makeText(this, "Đã xóa: ${person.name}", Toast.LENGTH_SHORT).show()
                val newList = personAdapter.currentList.toMutableList()
                newList.remove(person)
                personAdapter.submitList(newList)
            })

        val infor = binding.infor
        infor.layoutManager = LinearLayoutManager(this)   // Danh sách dọc
        infor.adapter = personAdapter

        binding.tv2.setOnClickListener {

            val name = binding.et1.text.toString()
            val phone = binding.et2.text.toString()

            if (name.isBlank() || phone.isBlank()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!phone.matches(Regex("^0\\d{9}$"))) {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPerson = Person(name, phone)
            val newList = personAdapter.currentList.toMutableList()
            newList.add(newPerson)
            personAdapter.submitList(newList)

            binding.et1.text.clear()
            binding.et2.text.clear()
        }

    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus // lấy view đang focus
        if (view is EditText) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager // lấy bộ quản lý bàn phím
            imm.hideSoftInputFromWindow(view.windowToken, 0) // ép bàn phím ẩn
            view.clearFocus() // bỏ focus
        }
        return super.dispatchTouchEvent(ev) // cho hệ thống chạy bình thường
    }
}