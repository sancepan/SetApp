package com.example.setapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val set_title: TextView = itemView.findViewById(R.id.set_title)
    val sets: TextView = itemView.findViewById(R.id.sets)
}