package com.example.setapp

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SetViewHolder(itemView: View, private val onItemListener: SetAdapter.OnItemListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val set_title: TextView = itemView.findViewById(R.id.set_title)
    //val setsRV: RecyclerView = itemView.findViewById(R.id.setsRV)
    val sets: GridLayout = itemView.findViewById(R.id.sets)

    init {
        itemView.setOnClickListener(this)
        //setsRV.layoutManager = layoutManager
    }

    override fun onClick(p0: View?) {
        onItemListener.onSetItemClick(adapterPosition, sets)
    }

}