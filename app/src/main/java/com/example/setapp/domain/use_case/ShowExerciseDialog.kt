package com.example.setapp.domain.use_case

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.setapp.R
import com.example.setapp.data.repository.ExerciseRepositoryImpl
import com.example.setapp.data.repository.WorkoutRepositoryImpl
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.use_case.exercise.AddExercise
import com.example.setapp.domain.use_case.workout.GetWorkoutIdByDate
import com.example.setapp.presentation.workout.ExerciseAdapter
import java.time.LocalDate

/** Диалоговое окно для создания упражнения */
class ShowExerciseDialog(private val context: Context) {

    private val exerciseRepository by lazy { ExerciseRepositoryImpl(context) }
    private val addExercise by lazy { AddExercise(exerciseRepository) }
    private val loadFromDBToMemory by lazy {  LoadFromDBToMemory(context) }

    private val workoutRepository by lazy { WorkoutRepositoryImpl(context) }
    private val getWorkoutIdByDate by lazy { GetWorkoutIdByDate(workoutRepository) }

    @SuppressLint("NotifyDataSetChanged")
    fun execute(activity: Activity, arrayList: ArrayList<Exercise>, adapter: ExerciseAdapter, date: LocalDate){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Удаляет фон у диалога
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.exercise_dialog)
        val nameTextView: EditText = dialog.findViewById(R.id.exercise_name)
        var name = ""
        val addBtn: Button = dialog.findViewById(R.id.addBtn)
        addBtn.setOnClickListener(View.OnClickListener {
            if (nameTextView.text.isEmpty()) {
                val toast = Toast.makeText(
                    context,
                    "Название не указано", Toast.LENGTH_SHORT
                )
                toast.show()
            } else {
                try {
                    // Получаем информацию из EditText
                    name = nameTextView.text.toString()

                    Log.e(ContentValues.TAG, "123" +  name)

                    // Добавляем упражнение в тренировку с указанным ID (в БД)
                    addExercise.execute(Exercise(0, name, ArrayList()), getWorkoutIdByDate.execute(date))
                    // Переносим данные из тренировки с указанным id в кеш
                    loadFromDBToMemory.execute(arrayList, getWorkoutIdByDate.execute(date))
                    // пересобираем адаптер
                    adapter.notifyDataSetChanged()

                    Log.e(ContentValues.TAG, "1234" +  arrayList.size + ", " + getWorkoutIdByDate.execute(date) )

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