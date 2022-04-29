package com.example.setapp.presentation.calendar

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.setapp.R

class CalendarHolder(view: View, private val onItemListener: CalendarAdapter.OnItemListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val daysOfMonth: TextView = view.findViewById(R.id.cellDayText)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onItemListener.onItemClick(adapterPosition, daysOfMonth.text as String)
    }

}