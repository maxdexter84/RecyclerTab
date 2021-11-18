package ru.maxdexter.recyclertab.ui.customview.range_indicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Toast
import kotlin.math.abs

const val normalRectHeight = 150

@SuppressLint("ResourceAsColor")
class IndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var currentY: Float = 0f
    private var currentX: Float = 0f
    private var rectangleHeight = 0f
    private val rectanglePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.RED
    }
    private val thumbPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5f
        strokeJoin = Paint.Join.ROUND
        isAntiAlias = true
    }
    private var figure: Square? = null
    private var currentYTop = 0f
    private var currentYBottom = 0f
    private var currentEventTouchY = 0f
    private val startPadding = 4f
    private var thumbTop = CircleThumb()
    private var thumbBottom = CircleThumb()
    private var motionTouchEventY = 0f
    private var motionTouchEventX = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private val endPadding = if (startPadding > 0) startPadding * 2 else startPadding
    var rectangleCreated: Boolean = false
    private var densityScale: Float = 0.0f

    init {
        isClickable = true
        initViews()
    }


    override fun onDraw(canvas: Canvas) {
        drawFigure(canvas)
    }

    private fun drawFigure(canvas: Canvas) {
        figure?.let {
            canvas.drawRect(
                it.x,
                it.y,
                it.x + it.width,
                it.y + it.height,
                it.color
            )
            canvas.drawCircle(thumbTop.x, thumbTop.y, thumbTop.radius, thumbPaint)
            canvas.drawCircle(thumbBottom.x, thumbBottom.y, thumbBottom.radius, thumbPaint)
            rectangleCreated = true
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        // saveNewSquare(event)
//            MotionEvent.ACTION_MOVE -> {
//                if (event.y > currentYTop && event.y < currentYBottom) {
//                    figure = Square(
//                        x = 0 + startPadding,
//                        y = event.y,
//                        color = rectanglePaint,
//                        width = width.toFloat() - endPadding,
//                        height = rectangleHeight.toFloat()
//                    )
//                    currentYTop = event.y - 35
//                    currentYBottom = event.y + rectangleHeight + 35
//                    currentEventTouchY = event.y
//                    // Toast.makeText(context, "Move", Toast.LENGTH_SHORT).show()
//                    invalidate()
//                }
//            }

        motionTouchEventY = event.y
        motionTouchEventX = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
            else -> {
                Toast.makeText(context, "$event.action", Toast.LENGTH_SHORT).show()
            }
        }




        return true
    }

    private fun touchUp() {

    }

    private fun touchMove() {
        currentX = motionTouchEventX
        currentY = if (motionTouchEventY < normalRectHeight) 0f else motionTouchEventY
        val distanceToTheBottom = measuredHeight - currentY
        rectangleHeight = findRectangleHeight(distanceToTheBottom)
        if (MathUtils.isPointInCircle(motionTouchEventX,motionTouchEventY, thumbTop.x, thumbTop.y,thumbTop.radius * 10) ||
            MathUtils.isPointInCircle(motionTouchEventX,motionTouchEventY, thumbBottom.x, thumbBottom.y,thumbBottom.radius * 10)) {
                     figure = Square(
                        x = 0 + startPadding,
                        y = motionTouchEventY,
                        color = rectanglePaint,
                        width = width.toFloat() - endPadding,
                        height = rectangleHeight.toFloat()
                    )
                    currentYTop = motionTouchEventY - 35
                    currentYBottom = motionTouchEventY + rectangleHeight + 35
                    currentEventTouchY = motionTouchEventY

            figure?.let {
                createNewThumb(it)
            }
            Toast.makeText(context, "thumb_click", Toast.LENGTH_SHORT).show()
            invalidate()
        }
    }

    private fun touchStart() {
        currentX = motionTouchEventX
        currentY = if (motionTouchEventY < normalRectHeight) 0f else motionTouchEventY
        val distanceToTheBottom = measuredHeight - currentY
        rectangleHeight = findRectangleHeight(distanceToTheBottom)
        if (!(motionTouchEventY > currentYTop && motionTouchEventY < currentYBottom) && !rectangleCreated) {
            figure = Square(
                x = 0 + startPadding,
                y = currentY,
                color = rectanglePaint,
                width = width.toFloat() - endPadding,
                height = rectangleHeight,
            )
            figure?.let {
                createNewThumb(it)
            }

            currentYTop = currentY - 20
            currentYBottom = currentY + rectangleHeight + 20
            currentEventTouchY = currentY
            rectangleCreated = true
        } else if ((motionTouchEventY > currentYTop && motionTouchEventY < currentYBottom) && rectangleCreated) {
            performClick()
        } else {
            figure = null
            currentYTop = 0f
            currentYBottom = 0f
            currentEventTouchY = 0f

            rectangleCreated = false
        }
        invalidate()

    }


    private fun initViews() {
        densityScale = context.resources.displayMetrics.density
        setWillNotDraw(false)
    }


    private fun findRectangleHeight(distanceToTheBottom: Float) =
        if (distanceToTheBottom < normalRectHeight) distanceToTheBottom else if (measuredHeight < normalRectHeight) measuredHeight.toFloat() else normalRectHeight.toFloat()

    private fun createNewThumb(figure: Square) {
        thumbBottom =
            CircleThumb(figure.x + figure.width - 50, figure.y + figure.height, figure.color, 10f)
        thumbTop = CircleThumb(figure.x + 50, figure.y, figure.color, 10f)

    }

    override fun performClick(): Boolean {
       // Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        return true
    }


    /**
     * Extension, переводящий dp в пиксели для отрисовки
     */
    private fun Float.toPx() = this * densityScale
}