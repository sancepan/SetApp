package com.example.setapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val cellSet: TextView = itemView.findViewById(R.id.cellSet)
}