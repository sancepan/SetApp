package com.example.setapp.domain.use_case

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.setapp.data.repository.ExerciseRepositoryImpl
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.use_case.exercise.GetExercises

/** Класс выгрузки данных из БД в кеш */
class LoadFromDBToMemory(context: Context) {

    private val exerciseRepository by lazy { ExerciseRepositoryImpl(context) }

    private val getExercises by lazy { GetExercises(exerciseRepository) }

    fun execute(arrayList: ArrayList<Exercise>, workoutID: Int){
        arrayList.clear()
        arrayList.addAll(ArrayList(getExercises.execute(workoutID)))
        Log.e(TAG, "1234: " + ArrayList(getExercises.execute(workoutID)).size + ", " + workoutID)
    }
}