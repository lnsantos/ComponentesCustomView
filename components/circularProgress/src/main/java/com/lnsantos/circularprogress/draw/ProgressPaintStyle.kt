package com.lnsantos.circularprogress.draw

import android.graphics.Paint

enum class ProgressPaintStyle(val style: Paint.Style) {
    STOKE(style = Paint.Style.STROKE),
    FILL(style = Paint.Style.FILL),
    FILL_AND_STROKE(style = Paint.Style.FILL_AND_STROKE)
}