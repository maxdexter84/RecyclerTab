package ru.maxdexter.recyclertab.ui.customview.customviews

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomRectangleEventIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rectangleX: Float = 0f
    private var rectangleY: Float = 0f
    private var circlePaint = Paint()
    private var circlePadding = 20f


    init {
        rectangleX = 50f
        rectangleY = 50f
        circlePaint.color = Color.RED
        circlePaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f



        canvas.drawRect(y, x, height + y, width + x, paint)

        canvas.drawCircle(x + circlePadding, y,10f,circlePaint)
        canvas.drawCircle(y + width - circlePadding, y + height, 5f,circlePaint)
    }


    enum class Thumb {
        NONE, START, END, BOTH
    }

    init {

    }


    companion object {
        private val displayMetrics = Resources.getSystem().displayMetrics
        private val density = displayMetrics.density
        private val invDensity = 1f / density
        private val sDensity = displayMetrics.scaledDensity
        fun dpToPx(value: Float): Float = value * density
        fun pxToDp(value: Float): Float = value * invDensity
        fun spToPx(value: Float): Float = value * sDensity

    }
}