package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter (
    private val onItemClick: (Person) -> Unit,
    private val onDeleteClick: (Person) -> Unit
): ListAdapter<Person,  PersonAdapter.ViewHolder>(PersonDiffCallBack()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_infor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val person = getItem(position)
        holder.tvName.text = person.name
        holder.tvPhone.text = person.phone

        holder.itemView.setOnClickListener { onItemClick(person) }
        holder.itemView.findViewById<ImageView>(R.id.ivDel).setOnClickListener { onDeleteClick(person) }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
    }
}