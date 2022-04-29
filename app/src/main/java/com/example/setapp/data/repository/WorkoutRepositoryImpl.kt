package com.example.setapp.data.repository

import android.content.Context
import android.database.Cursor
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.setapp.SQLiteManager
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.models.Workout
import com.example.setapp.domain.repository.WorkoutRepository
import java.time.LocalDate

class WorkoutRepositoryImpl(private val context: Context): WorkoutRepository {

    @RequiresApi(Build.VERSION_CODES.P)
    val dbHandler = SQLiteManager(context, null)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getWorkoutIdByDate(date: LocalDate): Int {
        val c: Cursor = dbHandler.getWorkoutByDate(date)
        var id = 0
        if (c != null) {
            while (c.moveToNext()) {
                id = c.getInt(0)
            }
            c.close()
        }

        return id
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun addWorkout(workout: Workout) {
        dbHandler.addWorkout(workout)
    }
}