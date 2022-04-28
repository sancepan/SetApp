package com.example.setapp.data.repository

import android.content.Context
import android.database.Cursor
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.setapp.SQLiteManager
import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.repository.ApproachRepository

class ApproachRepositoryImpl(private val context: Context): ApproachRepository {

    @RequiresApi(Build.VERSION_CODES.P)
    val dbHandler = SQLiteManager(context, null)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun addApproach(approach: Approach, exerciseID: Int)
    {
        dbHandler.addApproach(approach, exerciseID)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getApproaches(id: Int): ArrayList<Approach> {
        val c: Cursor = dbHandler.getApproaches(id)
        var data: ArrayList<Approach> = ArrayList()
        if (c != null) {
            while (c.moveToNext()) {
                data.add(Approach(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3)))
            }
            c.close()
        }
        return data
    }
}