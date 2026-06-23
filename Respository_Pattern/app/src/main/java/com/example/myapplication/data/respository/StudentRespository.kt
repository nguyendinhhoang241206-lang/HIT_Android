package com.example.myapplication.data.respository

import com.example.myapplication.data.local.dao.StudentDao
import com.example.myapplication.data.local.entity.Student

class StudentRepository {

    private val dao: StudentDao

    constructor(dao: StudentDao) {
        this.dao = dao
    }

    suspend fun addStudent(student: Student) {
        dao.insert(student)
    }

    suspend fun updateStudent(student: Student) {
        dao.update(student)
    }

    suspend fun deleteStudent(student: Student) {
        dao.delete(student)
    }

    suspend fun getAllStudents(): List<Student> {
        return dao.getAll()
    }

    suspend fun getStudentById(id: Int): Student? {
        return dao.getById(id)
    }
}