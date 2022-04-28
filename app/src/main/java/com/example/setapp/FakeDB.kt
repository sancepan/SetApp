package com.example.setapp

import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.models.Exercise

class FakeDB {
    /*private val approaches: ArrayList<Approach> = ArrayList()
    private val exercises: ArrayList<Exercise> = ArrayList()

    /** Заполняем FakeDB */
    fun createDB()
    {
        for(i in 0..4) approaches.add(Approach(i, 10 + i%2, 10))
        exercises.add(Exercise(0, "Жим штанги", approaches))
    }

    /** Добавить подход в упражнение с указанным id */
    private fun addApproach(idExercise: Int, approach: Approach)
    {
        getExercise(idExercise).approaches.add(approach)
    }

    /** Получить подход по его id и id его упражнения */
    private fun getApproach(idExercise: Int, idApproach: Int): Approach
    {
        for(i in 0 until getExercise(idExercise).approaches.size){
            if(getExercise(idExercise).approaches[i].id == idApproach) return getExercise(idExercise).approaches[i]
        }
        return getExercise(idExercise).approaches[0]
    }

    /** Получить все подходы по id пражнения */
    private fun getApproaches(idExercise: Int): ArrayList<Approach>
    {
        return getExercise(idExercise).approaches
    }

    /** Добавить упражнение */
    private fun addExercise(exercise: Exercise)
    {
        exercises.add(exercise)
    }

    /** Получить упражнение по id */
    private fun getExercise(idExercise: Int): Exercise
    {
        for(i in 0 until exercises.size)
        {
            if(exercises[i].id == idExercise) return exercises[i]
        }
        return exercises[0]
    }

    /** Получить все упражнения */
    private fun getExercises(): ArrayList<Exercise>
    {
        return exercises
    }*/


}