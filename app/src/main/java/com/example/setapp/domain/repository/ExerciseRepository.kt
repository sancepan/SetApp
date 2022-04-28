package com.example.setapp.domain.repository

import com.example.setapp.domain.models.Exercise

interface ExerciseRepository {

    fun addExercise(exercise: Exercise)
    fun getExercises(workouteID: Int): ArrayList<Exercise>
    fun deleteLastExercise(id: Int)

}