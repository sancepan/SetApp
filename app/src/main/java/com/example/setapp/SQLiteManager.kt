package com.example.setapp

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.models.Exercise
import com.example.setapp.domain.models.Workout
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.P)
class SQLiteManager(context: Context,
                    factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {

    //@SuppressLint("SimpleDateFormat")
    //private val dateFormat: DateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss")

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_WORKOUT_TABLE = ("CREATE TABLE " +
                WORKOUT_TABLE_NAME + "(" +
                WORKOUT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WORKOUT_COLUMN_DATE + " TEXT" +
                ")")

        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " +
                EXERCISE_TABLE_NAME + "(" +
                EXERCISE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXERCISE_COLUMN_NAME + " TEXT, " +
                EXERCISE_COLUMN_WORKOUT_ID + " INT" +
                ")")

        val CREATE_APPROACH_TABLE = ("CREATE TABLE " +
                APPROACH_TABLE_NAME + "(" +
                APPROACH_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                APPROACH_COLUMN_NUM + " INT," +
                APPROACH_COLUMN_WEIGHT + " INT," +
                APPROACH_COLUMN_REPS + " INT," +
                APPROACH_COLUMN_EXERCISE_ID + " INT" +
                ")")

        db.execSQL(CREATE_WORKOUT_TABLE)
        db.execSQL(CREATE_EXERCISE_TABLE)
        db.execSQL(CREATE_APPROACH_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $WORKOUT_TABLE_NAME")
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $EXERCISE_TABLE_NAME")
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $APPROACH_TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    fun getWorkoutByDate(date: LocalDate): Cursor{
        val db = this.readableDatabase
        //db.execSQL("DROP TABLE IF EXISTS " + APPROACH_TABLE_NAME);
        //val query = "SELECT * FROM $APPROACH_TABLE_NAME WHERE $APPROACH_COLUMN_EXERCISE_ID = ?"

        return db.query(
            WORKOUT_TABLE_NAME, arrayOf(WORKOUT_COLUMN_ID, WORKOUT_COLUMN_DATE),
            "$WORKOUT_COLUMN_DATE = ?", arrayOf(date.toString()),
            null, null, null
        )
    }

    fun addWorkout(workout: Workout){

        val values = ContentValues()
        values.put(WORKOUT_COLUMN_DATE, workout.date.toString())
        val db = this.writableDatabase
        db.insert(WORKOUT_TABLE_NAME, null, values)
        db.close()

    }

    /** Добавление подхода в упражнение с указанным ID */
    fun addApproach(approach: Approach, exerciseID: Int){
        val values = ContentValues()
        values.put(APPROACH_COLUMN_NUM, approach.num)
        values.put(APPROACH_COLUMN_WEIGHT, approach.weight)
        values.put(APPROACH_COLUMN_REPS, approach.reps)
        values.put(APPROACH_COLUMN_EXERCISE_ID, exerciseID)
        val db = this.writableDatabase
        db.insert(APPROACH_TABLE_NAME, null, values)
        db.close()
    }


    /** Получение подходов из упражнения с указанным ID */
    fun getApproaches(exerciseID: Int): Cursor{
        val db = this.readableDatabase
        //db.execSQL("DROP TABLE IF EXISTS " + APPROACH_TABLE_NAME);
        //val query = "SELECT * FROM $APPROACH_TABLE_NAME WHERE $APPROACH_COLUMN_EXERCISE_ID = ?"

        return db.query(
            APPROACH_TABLE_NAME, arrayOf(APPROACH_COLUMN_ID, APPROACH_COLUMN_NUM, APPROACH_COLUMN_WEIGHT, APPROACH_COLUMN_REPS, APPROACH_COLUMN_EXERCISE_ID),
            "$APPROACH_COLUMN_EXERCISE_ID = ?", arrayOf(exerciseID.toString()),
            null, null, null
        )
        //return db.rawQuery(query, arrayOf(exerciseID.toString()))
    }

    /** Добавление упражнения */
    fun addExercise(exercise: Exercise, workoutID: Int){
        val values = ContentValues()
        values.put(EXERCISE_COLUMN_NAME, exercise.name)
        values.put(EXERCISE_COLUMN_WORKOUT_ID, workoutID)
        val db = this.writableDatabase
        db.insert(EXERCISE_TABLE_NAME, null, values)
        Log.e(TAG, "1: " + exercise.name)
        db.close()
    }

    /** Получение упражнения по id Тренировки*/
    fun getExercises(workoutID: Int): Cursor{
        Log.e(TAG, "idDB: $workoutID")
        val db = this.readableDatabase
        //val query = "SELECT * FROM $EXERCISE_TABLE_NAME WHERE $EXERCISE_COLUMN_WORKOUT_ID = $workoutID"
        return db.query(
            EXERCISE_TABLE_NAME, arrayOf(EXERCISE_COLUMN_ID, EXERCISE_COLUMN_NAME, EXERCISE_COLUMN_WORKOUT_ID),
            "$EXERCISE_COLUMN_WORKOUT_ID = ?", arrayOf(workoutID.toString()),
            null, null, null
        )
    }

    /** Удаление упражнения с указанным ID */
    fun deleteExercisesById(id: Int){
        val db = this.readableDatabase
        db.delete(EXERCISE_TABLE_NAME, "$EXERCISE_COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "WorkoutDB"
        private const val DATABASE_VERSION = 2

        private const val WORKOUT_TABLE_NAME = "Workout"
        private const val WORKOUT_COLUMN_ID = "workoutID"
        private const val WORKOUT_COLUMN_DATE = "date"

        private const val EXERCISE_TABLE_NAME = "Exercise"
        private const val EXERCISE_COLUMN_ID = "ExerciseID"
        private const val EXERCISE_COLUMN_NAME = "name"
        private const val EXERCISE_COLUMN_WORKOUT_ID = "workoutID"

        private const val APPROACH_TABLE_NAME = "Approach"
        private const val APPROACH_COLUMN_ID = "ApproachID"
        private const val APPROACH_COLUMN_NUM = "num"
        private const val APPROACH_COLUMN_WEIGHT = "weight"
        private const val APPROACH_COLUMN_REPS = "reps"
        private const val APPROACH_COLUMN_EXERCISE_ID = "exerciseID"
    }
}