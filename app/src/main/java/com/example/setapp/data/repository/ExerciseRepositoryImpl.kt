package com.example.setapp.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.setapp.SQLiteManager
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.repository.ExerciseRepository
import com.example.setapp.domain.use_case.approach.GetApproaches

class ExerciseRepositoryImpl(private val context: Context): ExerciseRepository {

    private val approachRepository by lazy { ApproachRepositoryImpl(context) }

    private val getApproaches by lazy { GetApproaches(approachRepository) }

    @RequiresApi(Build.VERSION_CODES.P)
    val dbHandler = SQLiteManager(context, null)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getExercises(workoutID: Int): ArrayList<Exercise> {

        val c: Cursor = dbHandler.getExercises(workoutID)
        var data: ArrayList<Exercise> = ArrayList()
        if (c != null) {
            while (c.moveToNext()) {
                data.add(Exercise(c.getInt(0), c.getString(1), getApproaches.execute(c.getInt(0)) ))
            }
            c.close()
        }

        return data
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun deleteLastExercise(id: Int) {
        dbHandler.deleteExercisesById(id)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun addExercise(exercise: Exercise, workoutID: Int) {
        Log.e(TAG, "1: " + workoutID)
        dbHandler.addExercise(exercise, workoutID)
    }

}