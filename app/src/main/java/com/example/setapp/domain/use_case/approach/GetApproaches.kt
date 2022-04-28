package com.example.setapp.domain.use_case.approach

import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.repository.ApproachRepository

class GetApproaches(private val approachRepository: ApproachRepository) {
    fun execute(id: Int): ArrayList<Approach>{
        return approachRepository.getApproaches(id)
    }
}