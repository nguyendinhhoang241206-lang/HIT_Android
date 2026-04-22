package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClassroomAdapter: RecyclerView.Adapter<ClassroomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_classroom, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvNameClassroom: TextView = view.findViewById<TextView>(R.id.tvNameClass)

    }
}