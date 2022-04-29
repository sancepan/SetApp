package com.example.setapp.presentation

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.setapp.R
import com.example.setapp.data.repository.ExerciseRepositoryImpl
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.use_case.*
import com.example.setapp.domain.use_case.exercise.DeleteLastExercise
import com.example.setapp.presentation.calendar.CalendarAdapter
import com.example.setapp.presentation.workout.ExerciseAdapter
import com.example.setapp.presentation.workout.SpacesItemDecoration
import java.time.LocalDate


class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener,
    ExerciseAdapter.OnItemListener {

    /** TextView для загаловка с датой формата  MMMM-yyyy */
    private lateinit var monthYearText: TextView
    /** Экземпляр RecyclerView для календаря */
    private lateinit var calendarRecyclerView: RecyclerView
    /** Переменная, хранящая выбранную дату */
    private lateinit var selectedDate: LocalDate

    /** Экземпляр RecyclerView для календаря */
    private lateinit var setsRecycleView: RecyclerView

    /** Список упражнений, в которые входит название и списко подходов */
    private val setsList: ArrayList<Exercise> = ArrayList()

    private val exerciseRepository by lazy { ExerciseRepositoryImpl(applicationContext) }
    //private val approachRepository by lazy { ApproachRepositoryImpl(applicationContext) }

    //private val addApproach by lazy { AddApproach(approachRepository) }
    //private val addExercise by lazy { AddExercise(exerciseRepository) }
    // private val getApproaches by lazy { GetApproaches(approachRepository) }
    // private val getExercises by lazy { GetExercises(exerciseRepository) }
    private val deleteLastExercise by lazy { DeleteLastExercise(exerciseRepository) }

    private val loadFromDBToMemory by lazy {  LoadFromDBToMemory(applicationContext) }

    private val daysInMonthArray by lazy {  DaysInMonthArray() }

    private val showApproachDialog by lazy {  ShowApproachDialog(applicationContext) }

    private val showExerciseDialog by lazy {  ShowExerciseDialog(applicationContext) }



    /** Преобразует формат yyyy-MM-dd в MMMM-yyyy */
    private val monthYearFromDate by lazy {  MonthYearFromDate() }

    // Создаем объект адаптера и менеджера
    private val setAdapter by lazy { ExerciseAdapter(setsList, this) }

    // Создаем объект адаптера и менеджера
    //private val calendarAdapter by lazy { CalendarAdapter(daysInMonthArray.execute(selectedDate), this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFromDBToMemory.execute(setsList)
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

    /** Реагирование на свайпы по элементам RecyclerView */
    val myCallback = object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.RIGHT) {

        /** Функция реагирования на перемещение (реагирование отключено) */
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        /** Функция реагирования на свайп вправо по элементу RecycleView */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                              direction: Int) {
            deleteLastExercise.execute( setsList[viewHolder.adapterPosition].id )
            loadFromDBToMemory.execute(setsList)
            setAdapter.notifyDataSetChanged()
            if(setsList.isNotEmpty()) setsRecycleView.smoothScrollToPosition((setsRecycleView.adapter?.itemCount ?: 1) - 1)
        }

        /** Функция проверяющая, что свайп проходит по последнему элементу RecycleView */
        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return if (viewHolder.adapterPosition != setsList.size - 1) 0 else super.getSwipeDirs(
                recyclerView!!, viewHolder
            )
        }
    }

    /** Функция присвоения Layout-менеджера, адаптера, декорации и ItemTouchHelper-а
     * объекту RecyclerView */
    private fun setSetView()
    {

        // Создаем Layout-менеджер
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 1)
        // Присваиваем менеджер объекту RecyclerView
        setsRecycleView.layoutManager = layoutManager
        // Присваиваем адаптер объекту RecyclerView
        setsRecycleView.adapter = setAdapter
        // Добавляем декорацию (интервалы между элементами), если она не задана
        if (setsRecycleView.itemDecorationCount == 0 ) {
            setsRecycleView.addItemDecoration(SpacesItemDecoration(13))
        }
        // Создаем объект реагирования на свайпы
        val myHelper = ItemTouchHelper(myCallback)
        // Присваиваем myHelper объекту RecyclerView
        myHelper.attachToRecyclerView(setsRecycleView)
    }

    /** Пересобирает адаптер для нового ArrayList */
    private fun setMonthView()
    {

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)

        // Задаем заголовок
        monthYearText.text = monthYearFromDate.execute(selectedDate)

        val recyclerHeight = calendarRecyclerView.height

        // Создаем адаптер
        val calendarAdapter = CalendarAdapter(daysInMonthArray.execute(selectedDate), this)


        // Создаем Layout-менеджер
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        // Присваиваем Layout-менеджер объекту RecyclerView
        calendarRecyclerView.layoutManager = layoutManager
        // Присваиваем адаптер объекту RecyclerView
        calendarRecyclerView.adapter = calendarAdapter
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
        showApproachDialog.execute(this@MainActivity, position, setsList, setAdapter)
    }

    /** Обрабатывает нажатие на элемент календаря */
    override fun onItemClick(position: Int, dayText: String) {
        if(dayText != "")
        {
            val message: String = "Выбранная дата " + dayText + " " + monthYearFromDate.execute(selectedDate)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /** Функция добавления нового упражнения в ArrayList с последующим перезапуском адапетра */
    fun addWorkout(view: View)
    {
        showExerciseDialog.execute(this@MainActivity, setsList, setAdapter)
    }
}