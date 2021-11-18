package ru.maxdexter.recyclertab.ui.customview.range_indicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Toast

const val normalRectHeight = 150

@SuppressLint("ResourceAsColor")
class IndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var rectangleHeight = 0f
    private val rectanglePaint = Paint()
    private val thumbPaint = Paint()
    private var figure: Square? = null
    private var currentYTop = 0f
    private var currentYBottom = 0f
    private var currentEventTouchY = 0f
    private val startPadding = 4f
    private var thumbTop = CircleThumb()
    private var thumbBottom = CircleThumb()
    private val path = Path()

    private var motionTouchEventY = 0f
    private var motionTouchEventX = 0f

    private val endPadding = if (startPadding > 0) startPadding * 2 else startPadding
    var rectangleCreated: Boolean = false


    private var densityScale: Float = 0.0f

    init {

        isClickable = true
        rectanglePaint.style = Paint.Style.STROKE
        rectanglePaint.strokeWidth = 5f
        rectanglePaint.color = Color.RED
        thumbPaint.color = Color.RED
        thumbPaint.style = Paint.Style.FILL_AND_STROKE
        thumbPaint.strokeWidth = 5f
        thumbPaint.strokeJoin = Paint.Join.ROUND
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
            canvas.drawCircle(thumbTop.x, thumbTop.y, 10f, thumbPaint)
            canvas.drawCircle(thumbBottom.x , thumbBottom.y, 10f, thumbPaint)
            rectangleCreated = true
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action)  {
             MotionEvent.ACTION_MOVE  ->{
                 if(
                     (event.y > thumbTop.y - thumbTop.radius / 2 && event.y < thumbTop.y + thumbTop.radius / 2) &&
                     (event.x > thumbTop.x - thumbTop.radius / 2 && event.x < thumbTop.x + thumbTop.radius / 2)
                 ) {
//                     figure = Square(
//                        x = 0 + startPadding,
//                        y = event.y,
//                        color = rectanglePaint,
//                        width = width.toFloat() - endPadding,
//                        height = rectangleHeight.toFloat()
//                    )
//                    currentYTop = event.y - 35
//                    currentYBottom = event.y + rectangleHeight + 35
//                    currentEventTouchY = event.y
                    Toast.makeText(context, "MoveUp", Toast.LENGTH_SHORT).show()
                    invalidate()

                 }
            }
        }
        saveNewSquare(event)
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


//            motionTouchEventY = event.y
//            motionTouchEventX = event.x
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> touchStart()
//                MotionEvent.ACTION_MOVE -> touchMove()
//                MotionEvent.ACTION_UP -> touchUp()
//                else -> {
//                    Toast.makeText(context, "$event.action", Toast.LENGTH_SHORT).show()
//                }
//            }




        return true
    }

    private fun touchUp() {
        TODO("Not yet implemented")
    }

    private fun touchMove() {
        TODO("Not yet implemented")
    }

    private fun touchStart() {
        TODO("Not yet implemented")
    }


    private fun initViews() {
        densityScale = context.resources.displayMetrics.density
        setWillNotDraw(false)
    }

    private fun saveNewSquare(event: MotionEvent) {
        val currentY = if (event.y < normalRectHeight) 0f else event.y
        val distanceToTheBottom = measuredHeight - currentY
        rectangleHeight =
            if (distanceToTheBottom < normalRectHeight) distanceToTheBottom else if (measuredHeight < normalRectHeight) measuredHeight.toFloat() else normalRectHeight.toFloat()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!(event.y > currentYTop && event.y < currentYBottom) && !rectangleCreated) {
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
                    invalidate()
                    rectangleCreated = true
                } else if ((event.y > currentYTop && event.y < currentYBottom) && rectangleCreated) {
                    performClick()
                } else {
                    figure = null
                    currentYTop = 0f
                    currentYBottom = 0f
                    currentEventTouchY = 0f
                    invalidate()
                    rectangleCreated = false
                }
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Toast.makeText(context, "ACTION_OUTSIDE", Toast.LENGTH_SHORT).show()
                figure = null
                currentYTop = 0f
                currentYBottom = 0f
                currentEventTouchY = 0f
                invalidate()
                rectangleCreated = false
            }

        }
    }

    private fun createNewThumb(figure: Square) {
        thumbBottom = CircleThumb(figure.x + figure.width - 50 , figure.y + figure.height, figure.color, 10f)
        thumbTop = CircleThumb(figure.x + 50, figure.y, figure.color, 10f)

    }

    override fun performClick(): Boolean {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun initPaint(color: Int) {
        rectanglePaint.color = color
        thumbPaint.color = color
    }


    /**
     * Extension, переводящий dp в пиксели для отрисовки
     */
    private fun Float.toPx() = this * densityScale
}