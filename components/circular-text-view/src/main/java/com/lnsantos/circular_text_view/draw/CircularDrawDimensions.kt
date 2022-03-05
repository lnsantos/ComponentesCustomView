package com.lnsantos.circular_text_view.draw

internal data class CircularDrawDimensions(
    var widget: Int = 0,
    var height: Int = 0
){
    fun getDiameter() : Int = if (height > widget) height else widget
    fun getRadius() : Int = getDiameter() / 2
}
