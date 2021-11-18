package ru.maxdexter.recyclertab.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttributeSet: Int = 0
) : RecyclerView(context, attrs, defaultAttributeSet) {
    private val paint = Paint()
    private var densityScale: Float = 0.0f
    private var figure: Square = Square(0f, 0f, paint, 100f, 100f)

    init {
        this.layoutManager = LinearLayoutManager(context)
        paint.apply {
            color = Color.RED
            style = Paint.Style.FILL
        }
        initViews()
    }


    private fun initViews() {
        densityScale = context.resources.displayMetrics.density
        setWillNotDraw(false)
    }

    //Отрисовываем фигуру
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        c.drawRect(
            figure.y + figure.height,
            figure.x + figure.width,
            figure.y - figure.width,
            figure.x - figure.width,
            paint
        )


    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                val item = this.findChildViewUnder(e.x, e.y) as? EventRectangleView
                if (item != null) {
                    item.bottomBordersIsVisible = true
                    item.topBordersIsVisible = true
                    item.startBordersIsVisible = true
                    item.endBordersIsVisible = true

                    item.setBackgroundColor(Color.RED)
                    figure = Square(
                        x = item.x,
                        y = item.y,
                        color = paint,
                        width = item.width.toFloat().toPx(),
                        height = item.height.toFloat().toPx()
                    )
                }

            }
        }
        invalidate()
        return true
    }


    /**
     * Extension, переводящий dp в пиксели для отрисовки
     */
    private fun Float.toPx() = this * densityScale

}