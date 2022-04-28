package com.example.setapp.presentation.workout

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/** Декорация интервалов между элементами упражнений */
class SpacesItemDecoration(val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space
    }
}