package com.example.setapp.domain.use_case

import android.content.Context
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** Преобразует формат yyyy-MM-dd в MMMM-yyyy */
class MonthYearFromDate() {
    fun execute(date: LocalDate): String{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }
}