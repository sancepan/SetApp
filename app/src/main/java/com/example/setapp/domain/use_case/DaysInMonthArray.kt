package com.example.setapp.domain.use_case

import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

/** Создает ArrayList на месяц
 * (числа не входящие в месяц заполняются пустыми значениями,
 * всего 42 элемента) по заданной дате */
class DaysInMonthArray {
    fun execute(selectedDate: LocalDate): ArrayList<String>{
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(selectedDate)

        val daysInMonth: Int = yearMonth.lengthOfMonth()

        val firstOfMonth: LocalDate = selectedDate.withDayOfMonth(1)
        val dayOfWeek: Int = firstOfMonth.dayOfWeek.value - 1

        for(i in 1..42)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("")
            }
            else
            {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return  daysInMonthArray
    }
}