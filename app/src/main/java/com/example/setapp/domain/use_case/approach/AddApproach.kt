package com.example.setapp.domain.use_case.approach

import com.example.setapp.domain.models.Approach
import com.example.setapp.domain.repository.ApproachRepository

/** Добавление подхода */
class AddApproach(private val approachRepository: ApproachRepository) {
    fun execute(approach: Approach, exerciseID: Int){
        approachRepository.addApproach(approach, exerciseID)
    }
}