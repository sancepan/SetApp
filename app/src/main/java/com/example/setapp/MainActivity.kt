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

    val sets: ArrayList<Set> = ArrayList()

    var x: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Создаем объект подхода
        val newSet = Set(1, 15, 15)
        // Создаем массив из 5 объектов ранее созданного подхода
        for(i in 1..5) sets.add(newSet)

        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
        setSetView()
    }

    /** Связываем переменные с элементами UI (RecyclerView, monthYearTV) */
    private fun initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)

        setsRecycleView = findViewById(R.id.setsRecycleView)
    }

    /**  */
    private fun setSet()
    {

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

    /** Вычитает месяц переменной с датой */
    fun previousMonthAction(view: View)
    {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    /** Прибавляет месяц переменной с датой */
    fun nextMonthAction(view: View)
    {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    /** Обрабатывает нажатие на элемент упражнения */
    override fun onSetItemClick(position: Int, sets: GridLayout) {
        /*Log.e(TAG, "12345")
        // Создаем объект подхода
        val newSet = Set(1, 30, 1)
        setsList[position].sets.add(newSet)
        setAdapter.setsAdapter.notifyDataSetChanged()*/
        val newSetTextView: TextView = TextView(this)
        newSetTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            50F
        )

        /** Задаем вес для строчек и столбцов */
        val rowSpec: GridLayout.Spec  = GridLayout.spec(GridLayout.UNDEFINED, 1.0f)
        val colSpec: GridLayout.Spec  = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
        val params: GridLayout.LayoutParams  = GridLayout.LayoutParams(rowSpec, colSpec)

        //params.setGravity(Gravity.CENTER)
        when (x%3) {
            0 -> params.setGravity(Gravity.LEFT)
            1 -> params.setGravity(Gravity.CENTER)
            2 -> params.setGravity(Gravity.RIGHT)
            else -> {
                params.setGravity(Gravity.LEFT)
            }
        }

        x+=1


        newSetTextView.setTextColor(this.resources.getColorStateList(R.color.gray))
        newSetTextView.text = "1: [150x15] "

        sets.addView(newSetTextView, params)

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
        val newSetsCard = SetsCard(1, "Жим штанги", sets)
        // Создаем массив и добавляем в него ранее созданный список
        setsList.add(newSetsCard)
        // Пересобираем адаптер
        setAdapter.notifyDataSetChanged()
        // Скролим до добавленного элемента
        setsRecycleView.smoothScrollToPosition((setsRecycleView.adapter?.itemCount ?: 1) - 1)
    }
}