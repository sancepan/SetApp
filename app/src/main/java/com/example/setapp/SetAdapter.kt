package com.example.setapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Адаптер для карточки с упражнением (название упражнения и список RecycleView с подходами) */
class SetAdapter(private val setsList: ArrayList<SetsCard>, private val onItemListener: OnItemListener): RecyclerView.Adapter<SetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_item, parent, false)

        return SetViewHolder(view, onItemListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.set_title.text = setsList[position].name

        // Чтобы подходы не дублировались при прокрутке, удаляем все вьюшки
        holder.sets.removeAllViews()

        // Создаем объект TextView для каждого подхода, заполняем, настраиваем и добавляем в GridLayout
        for(i in 0 until setsList[position].sets.size)
        {
            // Создаем
            val newSetTextView = TextView(holder.sets.context)
            newSetTextView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                50F
            )
            // Заполняем
            newSetTextView.text = (setsList[position].sets[i].id + 1).toString() +
                    ": [ " + setsList[position].sets[i].weight.toString() + " x " +
                    setsList[position].sets[i].rep.toString() + " ]"
            // Настраиваем
            newSetTextView.setTextColor(Color.parseColor("#282828"))
            // Добавляем
            holder.sets.addView(newSetTextView)
        }
    }

    override fun getItemCount(): Int = setsList.size

    interface OnItemListener{
        fun onSetItemClick(position: Int, itemView: View)
    }

}