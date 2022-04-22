package com.example.setapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Адаптер для карточки с упражнением (название упражнения и список RecycleView с подходами) */
class SetAdapter(private val setsList: ArrayList<SetsCard>, private val onItemListener: OnItemListener): RecyclerView.Adapter<SetViewHolder>() {

    /** Экземпляр RecyclerView для списка с подходами */
    private lateinit var setRecycleView: RecyclerView
    /** Адаптер для списка с подходами */
    val setsAdapter = SetsAdapter(setsList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        /** Вьюшка с карточкой упражнения */
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.set_item, parent, false)

        /*/** Назначаем вьюшку RV экземпляру RV */
        setRecycleView = view.findViewById(R.id.setsRV)
        /** Создаем новый Layout менеджер */
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(parent.context, 3)
        /** Назначаем менеджер и адаптер экземпляру RV */
        setRecycleView.layoutManager = layoutManager
        setRecycleView.adapter = setsAdapter*/

        //val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(parent.context, 3)

        return SetViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.set_title.text = setsList[position].name
        /** Создаем новый Layout менеджер */
        //holder.setsRV.adapter = setsAdapter

        /*holder.sets.text = ""
        for(i in 0..4)
        {
            holder.sets.append(setsList[0].sets[i].id.toString() + ":[" +
                    setsList[0].sets[i].weight.toString() + "x" +
                    setsList[0].sets[i].rep.toString() + "], ")
        }*/
    }

    override fun getItemCount(): Int = setsList.size

    interface OnItemListener{
        fun onSetItemClick(position: Int, sets: GridLayout)
    }

}