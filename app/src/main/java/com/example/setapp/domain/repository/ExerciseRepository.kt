package com.example.setapp.domain.repository

import com.example.setapp.domain.models.Exercise

interface ExerciseRepository {

    fun addExercise(exercise: Exercise, workouteID: Int)
    fun getExercises(workouteID: Int): ArrayList<Exercise>
    fun deleteLastExercise(id: Int)

}