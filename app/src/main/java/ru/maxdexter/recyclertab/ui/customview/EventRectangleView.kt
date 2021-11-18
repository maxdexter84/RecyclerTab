package ru.maxdexter.recyclertab.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorLong
import androidx.core.content.withStyledAttributes
import ru.maxdexter.recyclertab.R


class EventRectangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttributeSet: Int = 0,
) : View(context, attrs, defaultAttributeSet) {

    var isUserOwnEvent = false
        set(value) {
            field = value
            invalidate()
        }
    var topBordersIsVisible = true
        set(value) {
            field = value
            invalidate()
        }
    var bottomBordersIsVisible = true
        set(value) {
            field = value
            invalidate()
        }
    var startBordersIsVisible = true
        set(value) {
            field = value
            invalidate()
        }
    var endBordersIsVisible = true
        set(value) {
            field = value
            invalidate()
        }

    private var startTime = 0
    private var finishTime = 0

    private lateinit var painterEvent: Paint
    private lateinit var painterDiagonalLineEvent: Paint

    private lateinit var painterBorderLine: Paint
    private lateinit var painterDelimiterLine: Paint

    private lateinit var painterDelimiterTime: Paint

    init {
        context.withStyledAttributes(attrs, R.styleable.EventRectangleView) {
            isUserOwnEvent = getBoolean(R.styleable.EventRectangleView_isUserOwnEvent, false)
            topBordersIsVisible =
                getBoolean(R.styleable.EventRectangleView_topBordersIsVisible, true)
            bottomBordersIsVisible =
                getBoolean(R.styleable.EventRectangleView_bottomBordersIsVisible, true)
            startBordersIsVisible =
                getBoolean(R.styleable.EventRectangleView_startBordersIsVisible, true)
            endBordersIsVisible =
                getBoolean(R.styleable.EventRectangleView_endBordersIsVisible, true)
        }

        initPainters()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawEventBackground(width.toFloat(), height.toFloat(), canvas)
            drawTimePeriod(width.toFloat(), height.toFloat(), canvas)
            drawBorders(width.toFloat(), height.toFloat(), canvas)
        }
    }

    private fun drawBorders(width: Float, height: Float, canvas: Canvas) {

        if (startBordersIsVisible) canvas.drawLine(0f, 0f, 0f, height, painterDelimiterLine)
        if (topBordersIsVisible) canvas.drawLine(0f, 0f, width, 0f, painterDelimiterLine)
        if (bottomBordersIsVisible) canvas.drawLine(0f, height, width, height, painterDelimiterLine)
        if (endBordersIsVisible) canvas.drawLine(width, 0f, width, height, painterDelimiterLine)

        if (startBordersIsVisible) canvas.drawLine(0f, 0f, 0f, height, painterBorderLine)
        if (topBordersIsVisible) canvas.drawLine(0f, 0f, width, 0f, painterBorderLine)
        if (bottomBordersIsVisible) canvas.drawLine(0f, height, width, height, painterBorderLine)
        if (endBordersIsVisible) canvas.drawLine(width, 0f, width, height, painterBorderLine)
    }

    private fun drawTimePeriod(width: Float, height: Float, canvas: Canvas) {
        canvas.drawRect(
            0f,
            finishTime / minutes.toFloat() * height,
            width,
            height,
            painterDelimiterTime
        )
        canvas.drawRect(
            0f,
            0f,
            width,
            startTime / minutes.toFloat() * height,
            painterDelimiterTime
        )

    }

    private fun drawEventBackground(width: Float, height: Float, canvas: Canvas) {
        canvas.drawRect(0f, 0f, width, height, painterEvent)
        painterDiagonalLineEvent.strokeWidth = width / 15f
        val dividingPartScreen = height / 3f

        if (isUserOwnEvent) {
            for (i in 1..20) {
                canvas.drawLine(
                    dividingPartScreen * i.toFloat(),
                    -height,
                    -width,
                    dividingPartScreen * i.toFloat(),
                    painterDiagonalLineEvent
                )
            }
        }
    }

    private fun initPainters() {
        painterEvent = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        painterDiagonalLineEvent = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.STROKE
            alpha = 150
        }

        painterBorderLine = Paint().apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 2f
        }

        painterDelimiterLine = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 6f
        }

        painterDelimiterTime = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    fun setColorEvent(@ColorLong color: Int) {
        painterEvent.color = color
        invalidate()
    }

    fun setMinuteInterval(startMinute: Int, finishMinute: Int) {
        if (startMinute >= minutes || startMinute < 0) this.startTime = 0
        else this.startTime = startMinute
        when {
            finishMinute < startMinute -> this.finishTime = startMinute
            finishMinute > minutes -> this.finishTime = minutes
            else -> this.finishTime = finishMinute
        }
        invalidate()
    }

    companion object {
        private const val minutes = 60
    }
}