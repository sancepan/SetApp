package com.example.setapp.domain.use_case.exercise

import android.content.ContentValues.TAG
import android.util.Log
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.repository.ExerciseRepository

class GetExercises (private val exerciseRepository: ExerciseRepository) {
    fun execute(workoutID: Int): ArrayList<Exercise>
    {
        Log.e(TAG, "idUseCase: $workoutID")
        return exerciseRepository.getExercises(workoutID)
    }
}