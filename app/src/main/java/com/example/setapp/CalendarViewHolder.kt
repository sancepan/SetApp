package com.example.setapp

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(itemView: View, _onItemListener: CalendarAdapter.OnItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    /** TextView для элемента (числа) календаря */
    val daysOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    /** Экземпляр OnItemListener для обработки нажатий на элемент календаря*/
    private val onItemListener: CalendarAdapter.OnItemListener = _onItemListener

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onItemListener.onItemClick(adapterPosition, daysOfMonth.text as String)
    }

}