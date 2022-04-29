package com.example.setapp.domain.models

import java.time.LocalDate

class Workout (var id: Int, val date: LocalDate, val exercise: ArrayList<Exercise>) {
}