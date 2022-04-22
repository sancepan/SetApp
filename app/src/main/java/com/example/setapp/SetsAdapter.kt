package com.example.setapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SetsAdapter (private val setsList: ArrayList<SetsCard>): RecyclerView.Adapter<SetsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_cell, parent, false)
        return SetsHolder(view);
    }

    override fun onBindViewHolder(holder: SetsHolder, position: Int) {
        holder.cellSet.text = setsList[0].sets[0].id.toString() + ":[" +
                setsList[0].sets[0].weight.toString() + "x" +
                setsList[0].sets[0].rep.toString() + "]"
    }

    override fun getItemCount(): Int = setsList.size
}