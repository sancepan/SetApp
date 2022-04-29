package com.example.setapp.domain.use_case.workout

import com.example.setapp.domain.models.Workout
import com.example.setapp.domain.repository.WorkoutRepository

/** Добавление тренировки */
class AddWorkout(private val workoutRepository: WorkoutRepository) {
    fun execute(workout: Workout){
        workoutRepository.addWorkout(workout)
    }
}