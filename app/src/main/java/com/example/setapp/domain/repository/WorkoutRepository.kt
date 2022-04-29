package com.example.setapp.domain.repository

import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.models.Workout
import java.time.LocalDate

interface WorkoutRepository {

    fun getWorkoutIdByDate(date: LocalDate): Int
    fun addWorkout(workout: Workout)

}