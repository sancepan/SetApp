package com.example.setapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener, SetAdapter.OnItemListener{

    /** TextView для загаловка с датой формата  MMMM-yyyy */
    private lateinit var monthYearText: TextView
    /** Экземпляр RecyclerView для календаря */
    private lateinit var calendarRecyclerView: RecyclerView
    /** Переменная, хранящая выбранную дату */
    private lateinit var selectedDate: LocalDate

    /** Экземпляр RecyclerView для календаря */
    private lateinit var setsRecycleView: RecyclerView

    /** Список упражнений, в которые входит название и списко подходов */
    private val setsList: ArrayList<SetsCard> = ArrayList()
    // Создаем объект адаптера и менеджера
    private val setAdapter = SetAdapter(setsList, this)

    val sets1: ArrayList<Set> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createData()
        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
        setSetView()
    }

    private fun createData()
    {
        // Создаем 5 объектов подхода
        for(i in 0..4) sets1.add(Set(i, 10 + i%2, 10))
        // Создаем 2 объкта упражнений с этими подходами
        setsList.add(SetsCard(0, "Жим штанги", sets1))
    }

    /** Связываем переменные с элементами UI (RecyclerView, monthYearTV) */
    private fun initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)

        setsRecycleView = findViewById(R.id.setsRecycleView)
    }

    /** Пересобирает адаптер для нового ArrayList */
    private fun setSetView()
    {
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 1)
        // Присваеваем ранее созданные объекты адаптера и менеджера объекту RecycleView
        setsRecycleView.layoutManager = layoutManager
        setsRecycleView.adapter = setAdapter

        // Если декорация не задана, добавляем ее (инетрвал между элементами)
        // Без проверки инетрвал будет увеличиваться при каждом вызове функции
        if (setsRecycleView.itemDecorationCount == 0 ) {
            setsRecycleView.addItemDecoration(SpacesItemDecoration(13))
        }
    }

    /** Пересобирает адаптер для нового ArrayList */
    private fun setMonthView()
    {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    /** Создает ArrayList на месяц
     * (числа не входящие в месяц заполняются пустыми значениями,
     * всего 42 элемента) по заданной дате */
    private fun daysInMonthArray(date: LocalDate): ArrayList<String>
    {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)

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

    /** Преобразует формат yyyy-MM-dd в MMMM-yyyy */
    private fun monthYearFromDate(date: LocalDate): String
    {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    /** Вычитает месяц для переменной с датой */
    fun previousMonthAction(view: View)
    {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    /** Прибавляет месяц для переменной с датой */
    fun nextMonthAction(view: View)
    {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    /** Обрабатывает нажатие на элемент упражнения */
    override fun onSetItemClick(position: Int, itemView: View) {
        // Добавляем новый подход в список
        setsList[position].sets.add(Set(setsList[position].sets.size, 13, 12))
        // Пересобираем адаптер по новому списку
        setAdapter.notifyDataSetChanged()
    }

    /** Обрабатывает нажатие на элемент календаря */
    override fun onItemClick(position: Int, dayText: String) {
        if(dayText != "")
        {
            val message: String = "Выбранная дата " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /** Функция добавления нового упражнения в ArrayList с последующим перезапуском адапетра */
    fun addSet(view: View)
    {
        // Создаем объект списка подходов
        val newSetsCard = SetsCard(1, "Жим штанги", ArrayList())
        // Создаем массив и добавляем в него ранее созданный список
        setsList.add(newSetsCard)
        // Пересобираем адаптер
        setAdapter.notifyDataSetChanged()
        // Скролим до добавленного элемента
        setsRecycleView.smoothScrollToPosition((setsRecycleView.adapter?.itemCount ?: 1) - 1)
    }
}