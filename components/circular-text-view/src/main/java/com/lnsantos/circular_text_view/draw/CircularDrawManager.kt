package com.lnsantos.circular_text_view.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

internal class CircularDrawManager() {

    private val paint = Paint()
    private val dimensions = CircularDrawDimensions()

    init {
        paint.flags = Paint.ANTI_ALIAS_FLAG
    }

    fun setColor(color: Int) : CircularDrawManager = apply {
        paint.color = color
    }

    fun setDimens(widget: Int, height: Int) : CircularDrawManager = apply {
        dimensions.apply { this.widget = widget / 2; this.height = height }
    }

    fun onSetupDraw(canvas: Canvas, notifySizeChanged: NotifySizeChanged) : Canvas = canvas.apply {
        val sizeCircular = dimensions.getRadius().toFloat()

        notifySizeChanged(dimensions.getDiameter(), dimensions.getDiameter())

        drawCircle(
            sizeCircular,
            sizeCircular,
            sizeCircular,
            paint
        )
    }

}
