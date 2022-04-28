package com.example.setapp.presentation.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.setapp.R

/** Адаптер для заполнения календаря числами из списка daysOfMonth */
class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: OnItemListener): RecyclerView.Adapter<CalendarHolder>(){

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CalendarHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = ((parent.height * 0.166666).toInt())

        return CalendarHolder(view, onItemListener)
    }

    override fun onBindViewHolder(@NonNull holder: CalendarHolder, position: Int) {
        holder.daysOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount() = daysOfMonth.size

    interface OnItemListener{
        fun onItemClick(position: Int, dayText: String)
    }

}