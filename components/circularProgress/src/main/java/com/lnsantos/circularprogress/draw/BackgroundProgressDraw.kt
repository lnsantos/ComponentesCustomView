package com.lnsantos.circularprogress.draw

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.annotation.ColorInt

class BackgroundProgressDraw : RectF(){

    private var width: Float = 0f
    private var height: Float = 0f

    private var backgroundPaint = Paint()
    var boundsCoordinates = Rect()

    fun getPaint() = backgroundPaint
    fun getBackground() : RectF = this

    init {
        backgroundPaint.isDither = true
    }

    fun setupDimens(width: Float, height: Float){
        this.width = width
        this.height = height
    }

    fun initialBackground(paddingAll: Float){
        set(
            paddingAll,
            paddingAll,
            width - paddingAll,
            height - paddingAll
        )
    }

    fun initialBackgroundWithBoundsCoordinates(paddingAll: Float){
        set(
            paddingAll,
            paddingAll,
            boundsCoordinates.right - paddingAll,
            boundsCoordinates.bottom - paddingAll
        )
    }

    fun initialDefaultPaint(){
        setColor(Color.BLACK)
        setPaintStyle(ProgressPaintStyle.STOKE)
        setStrokeWidth(60.0f)
        backgroundPaint.isAntiAlias = true
    }

    fun setColor(@ColorInt color: Int){
        backgroundPaint.color = color
    }

    fun setPaintStyle(style: ProgressPaintStyle){
        backgroundPaint.style = style.style
    }

    fun setStrokeWidth(value: Float){
        backgroundPaint.strokeWidth = value
    }

    fun enableEdge(){
        backgroundPaint.strokeCap = Paint.Cap.ROUND
    }

}