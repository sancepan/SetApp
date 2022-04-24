package com.example.setapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/** Адаптер для заполнения календаря числами из списка daysOfMonth */
class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener): RecyclerView.Adapter<CalendarViewHolder>(){

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = ((parent.height * 0.166666).toInt())

        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(@NonNull holder: CalendarViewHolder, position: Int) {
        holder.daysOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount() = daysOfMonth.size

    interface OnItemListener{
        fun onItemClick(position: Int, dayText: String)
    }

}