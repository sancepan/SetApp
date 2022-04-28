package com.example.setapp.domain.use_case.exercise

import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.repository.ExerciseRepository

/** Добавление упражнения */
class AddExercise(private val exerciseRepository: ExerciseRepository) {
    fun execute(exercise: Exercise)
    {
        exerciseRepository.addExercise(exercise)
    }
}