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
import com.example.setapp.data.repository.ExerciseRepositoryImpl
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.use_case.exercise.AddExercise
import com.example.setapp.presentation.workout.ExerciseAdapter

/** Диалоговое окно для создания упражнения */
class ShowExerciseDialog(private val context: Context) {

    private val exerciseRepository by lazy { ExerciseRepositoryImpl(context) }
    private val addExercise by lazy { AddExercise(exerciseRepository) }
    private val loadFromDBToMemory by lazy {  LoadFromDBToMemory(context) }

    fun execute(activity: Activity, arrayList: ArrayList<Exercise>, adapter: ExerciseAdapter) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Удаляет фон у диалога
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.exercise_dialog)
        val name: EditText = dialog.findViewById(R.id.exercise_name)
        val addBtn: Button = dialog.findViewById(R.id.addBtn)
        addBtn.setOnClickListener(View.OnClickListener {
            if (name.text.isEmpty()) {
                val toast = Toast.makeText(
                    context,
                    "Название не указано", Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                try {
                    // Получаем информацию из EditText
                    val name = name.text.toString()

                    addExercise.execute(Exercise(0, name, ArrayList()))
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