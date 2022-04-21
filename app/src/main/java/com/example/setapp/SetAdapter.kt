package com.example.setapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SetAdapter(private val setsList: ArrayList<SetsCard>): RecyclerView.Adapter<SetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_item, parent, false)
        return SetViewHolder(view);
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.set_title.text = setsList[0].name
        for(i in 1..4)
        {
            holder.sets.append(setsList[0].sets[i].id.toString() + ": " +
                    setsList[0].sets[i].weight.toString() + "x" +
                    setsList[0].sets[i].rep.toString())
        }
    }

    override fun getItemCount(): Int = setsList.size
}