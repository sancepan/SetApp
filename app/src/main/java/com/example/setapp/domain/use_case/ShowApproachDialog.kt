package com.example.setapp.domain.use_case

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.setapp.R
import com.example.setapp.data.repository.ApproachRepositoryImpl
import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.use_case.approach.AddApproach
import com.example.setapp.presentation.workout.ExerciseAdapter

/** Диалоговое окно для создания подхода */
class ShowApproachDialog(private val context: Context) {

    private val approachRepository by lazy { ApproachRepositoryImpl(context) }
    private val addApproach by lazy { AddApproach(approachRepository) }
    private val loadFromDBToMemory by lazy {  LoadFromDBToMemory(context) }

    fun execute(activity: Activity, position: Int, arrayList: ArrayList<Exercise>, adapter: ExerciseAdapter) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Удаляет фон у диалога
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.approach_dialog)
        val weightBox: EditText = dialog.findViewById(R.id.weight)
        val repsBox: EditText = dialog.findViewById(R.id.rep)
        val addBtn: Button = dialog.findViewById(R.id.addBtn)
        addBtn.setOnClickListener(View.OnClickListener {
            if (weightBox.text.isEmpty() and repsBox.text.isEmpty()) {
                val toast = Toast.makeText(
                    context,
                    "Не указан вес и количество повторений", Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (weightBox.text.isEmpty()) {
                val toast = Toast.makeText(
                    context,
                    "Не указан вес", Toast.LENGTH_SHORT
                )
                toast.show()
            } else if (repsBox.text.isEmpty()) {
                val toast = Toast.makeText(
                    context,
                    "Не указано количество повторений", Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                try {
                    // Получаем информацию из EditText
                    val weight = weightBox.text.toString().toInt()
                    val reps = repsBox.text.toString().toInt()

                    addApproach.execute(Approach(0, arrayList[position].approaches.size, weight, reps), arrayList[position].id)
                    loadFromDBToMemory.execute(arrayList)
                    adapter.notifyDataSetChanged()

                    dialog.dismiss()
                } catch (e: Exception) {
                    val toast = Toast.makeText(
                        context,
                        "Поля были некоректно заполнены", Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }
        })
        dialog.show()
    }
}