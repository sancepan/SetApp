package com.example.setapp.domain.use_case.exercise

import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.repository.ExerciseRepository

class GetExercises (private val exerciseRepository: ExerciseRepository) {
    fun execute(): ArrayList<Exercise>
    {
        return exerciseRepository.getExercises(1)
    }
}