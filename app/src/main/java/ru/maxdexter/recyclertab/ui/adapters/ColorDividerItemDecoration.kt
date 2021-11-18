package ru.maxdexter.recyclertab.ui.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class ColorDividerItemDecoration : ItemDecoration() {
    private var mDividerHeight = 0f
    private val mPaint: Paint = Paint()

    init {
        mPaint.setAntiAlias(true)
        mPaint.setColor(Color.RED)
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

// // Первый ItemView не должен рисовать разделительную линию на нем
        if (parent.getChildAdapterPosition(view) != 0) {
            // Здесь прямо прописан в 1px
            outRect.top = 2
            mDividerHeight = 2f
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view: View = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            // Первый ItemView не нужно рисовать
            if (index == 0) {
                continue
            }
            val dividerTop: Float = view.getTop() - mDividerHeight
            val dividerLeft = parent.paddingLeft.toFloat()
            val dividerBottom: Float = view.getTop().toFloat()
            val dividerRight = (parent.width - parent.paddingRight).toFloat()
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint)
        }
    }





}