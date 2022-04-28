package com.example.setapp.presentation.workout

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.setapp.R

class ExerciseHolder(itemView: View, private val onItemListener: ExerciseAdapter.OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val set_title: TextView = itemView.findViewById(R.id.set_title)
    val sets: GridLayout = itemView.findViewById(R.id.sets)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onItemListener.onSetItemClick(adapterPosition, itemView)
    }

}