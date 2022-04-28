package com.example.setapp.presentation.workout

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.setapp.R
import com.example.setapp.domain.models.Exercise

/** Адаптер для карточки с упражнением (название упражнения и список RecycleView с подходами) */
class ExerciseAdapter(private val setsList: ArrayList<Exercise>, private val onItemListener: OnItemListener): RecyclerView.Adapter<ExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_item, parent, false)

        return ExerciseHolder(view, onItemListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.set_title.text = setsList[position].name

        // Чтобы подходы не дублировались при прокрутке, удаляем все вьюшки
        holder.sets.removeAllViews()

        // Создаем объект TextView для каждого подхода, заполняем, настраиваем и добавляем в GridLayout
        for(i in 0 until setsList[position].approaches.size)
        {
            // Создаем
            val newSetTextView = TextView(holder.sets.context)
            newSetTextView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                50F
            )
            // Заполняем
            newSetTextView.text = (setsList[position].approaches[i].num + 1).toString() +
                    ": [ " + setsList[position].approaches[i].weight.toString() + " x " +
                    setsList[position].approaches[i].reps.toString() + " ]"
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