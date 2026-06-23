package com.example.myapplication.ui.student

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.entity.Student
import com.example.myapplication.data.respository.StudentRepository
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val students = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    private val viewModel: StudentViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StudentRepository(database.studentDao())
        StudentViewModelFactory(repository)
    }

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

        adapter = StudentAdapter(students)
        binding.rvStudent.layoutManager = LinearLayoutManager(this)
        binding.rvStudent.adapter = adapter

        // Observe changes from ViewModel
        viewModel.students.observe(this) { studentList ->
            students.clear()
            students.addAll(studentList)
            adapter.notifyDataSetChanged()
        }

        // Initial data load
        viewModel.loadStudents()

        binding.btnAdd.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()

            if (name.isNotBlank() && age.isNotBlank()) {
                viewModel.addStudent(name, age.toInt())
                binding.edtName.text.clear()
                binding.edtAge.text.clear()
            }
        }
    }
}