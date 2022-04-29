package com.example.setapp.domain.use_case.workout

import com.example.setapp.domain.repository.WorkoutRepository
import java.time.LocalDate

class GetWorkoutIdByDate(private val workoutRepository: WorkoutRepository) {
    fun execute(date: LocalDate): Int{
        return workoutRepository.getWorkoutIdByDate(date)
    }
}