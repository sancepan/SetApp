package com.example.setapp.domain.use_case.exercise

import com.example.setapp.domain.repository.ExerciseRepository

class DeleteLastExercise(private val exerciseRepository: ExerciseRepository) {
    fun execute(id: Int){
        exerciseRepository.deleteLastExercise(id)
    }

}