package com.lnsantos.circular_text_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.lnsantos.circular_text_view.draw.CircularDrawManager

class CircularTextView @JvmOverloads constructor(
    private val ctx: Context,
    private val attr: AttributeSet? = null,
    private val defStyle: Int = 0
) : AppCompatTextView(ctx, attr, defStyle) {

    companion object {
        private const val DEFAULT_COLOR = 0
    }

    private val circularDrawManager = CircularDrawManager()

    init {
         setupAttributes()
    }

    private fun setupAttributes() {
        ctx.obtainStyledAttributes(attr, R.styleable.CircularTextView)?.apply {

            getColor(
                R.styleable.CircularTextView_backgroundCircular, DEFAULT_COLOR).isDifferent(DEFAULT_COLOR)?.let { backgroundColor ->
                circularDrawManager.setColor(backgroundColor)
            }

            recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        circularDrawManager.setDimens(width / 2, height)
        var currentCanvas: Canvas? = canvas

        canvas?.also {
            currentCanvas = circularDrawManager.onSetupDraw(canvas) { width, height ->
                this.height = height
                this.width = width
            }
        }

        super.onDraw(currentCanvas)
    }

    private fun <T : Any> T.isDifferent(data: T) : T? = takeIf { this != data }

}