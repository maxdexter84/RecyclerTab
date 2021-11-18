package ru.maxdexter.recyclertab.ui.customview.range_indicator

import kotlin.math.sqrt

object MathUtils {
    fun isPointInCircle(
        x: Float,
        y: Float,
        cx: Float,
        cy: Float,
        radius: Float
    ): Boolean {
        return sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy)) < radius
    }
}