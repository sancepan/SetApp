package com.example.setapp.domain.repository

import com.example.setapp.domain.models.Approach

interface ApproachRepository {
    fun addApproach(approach: Approach, exerciseID: Int)
    fun getApproaches(id: Int): ArrayList<Approach>
}