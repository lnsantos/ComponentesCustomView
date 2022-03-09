package com.lnsantos.circularprogress

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.lnsantos.circularprogress.draw.BackgroundProgressDraw

class CircularProgress @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defTheme: Int = 0
) : View(ctx, attrs, defTheme) {

    companion object {
        private const val DEFAULT_ANGLE = 0.0F
        private const val DEFAULT_CIRCLE = 360.0F
        private const val DEFAULT_CIRCLE_PADDING = 40.0F
        private const val DEFAULT_START_ANGLE = 270F
        private const val DEFAULT_BACKGROUND_SIZE = 60.0F
        private const val DEFAULT_PROGRESS_ANGLE = 0.0F
    }

    private val backgroundProgress = BackgroundProgressDraw()
    private val mainProgress = BackgroundProgressDraw()

    private var circleBackgroundSize = DEFAULT_BACKGROUND_SIZE
    private var circleMainBackgroundSize = DEFAULT_BACKGROUND_SIZE / 2

    private var circularDisableColor = Color.GRAY
    private var circularBackgroundColor = Color.GRAY


    private var circularMaxProcessBackgroundColor = Color.GRAY
    private var circularProcessBackgroundColor = Color.GRAY

    private var progressBarAngleValue = DEFAULT_PROGRESS_ANGLE
        set(value) {
            field = (360.0f / 100 * value).also {
                Log.d("CircularProgress", "Current progress $it")
            }
        }

    private var isProgressMin = true
    private var isProgressMax = false

    fun setProgress(progress: Float) {
        isProgressMin = progress < 1
        isProgressMax = progress > 99
        progressBarAngleValue = progress
        invalidate()
    }

    init {
        backgroundProgress.initialDefaultPaint()
        mainProgress.initialDefaultPaint()
        setupAttributes()
    }

    private fun setupAttributes() {
        context.obtainStyledAttributes(attrs, R.styleable.CircularProgress).run {

            getColor(
                R.styleable.CircularProgress_circularDisableBackgroundColor,
                Color.GRAY
            ).let { color ->
                circularDisableColor = color
            }

            getColor(
                R.styleable.CircularProgress_circularBackgroundColor,
                Color.BLACK
            ).let { color ->
                circularBackgroundColor = color
            }

            getColor(
                R.styleable.CircularProgress_circularProgressBackgroundColor,
                Color.YELLOW
            ).let { color ->
                circularProcessBackgroundColor = color
            }

            getFloat(
                R.styleable.CircularProgress_circularSizeBackground,
                DEFAULT_BACKGROUND_SIZE
            ).let { size ->
                circleBackgroundSize = size
            }

            getFloat(
                R.styleable.CircularProgress_circularSizeProgressBackground,
                circleMainBackgroundSize
            ).let { size ->
                circleMainBackgroundSize = size
            }

            getFloat(
                R.styleable.CircularProgress_circularProgress,
                DEFAULT_ANGLE
            ).let { progress ->
                progressBarAngleValue = progress
            }

            getColor(
                R.styleable.CircularProgress_circularMaxProgressBackgroundColor,
                0
            ).let { color ->
                circularMaxProcessBackgroundColor = color
            }

            recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircleBackground()
        canvas?.getClipBounds(mainProgress.boundsCoordinates)
        canvas?.drawCircleProgress()
        canvas?.drawText()

        super.onDraw(canvas)
    }

    private fun Canvas?.drawText() {

        val percent = (progressBarAngleValue / 360) * 100
        val textData = when {
            isProgressMax && isProgressMin.not() -> "Completado"
            isProgressMin && isProgressMin -> "Sem informações"
            else -> "${percent.toInt()}"
        }

        val textColor = when {
            isProgressMax -> circularMaxProcessBackgroundColor
            isProgressMin -> circularDisableColor
            else -> circularProcessBackgroundColor
        }

        val textSizeValue = when {
            isProgressMax && isProgressMin.not() || isProgressMin && isProgressMin -> (circleBackgroundSize * 100 / circleMainBackgroundSize) / (textData.length / 4)
            else -> circleBackgroundSize * 100 / circleMainBackgroundSize
        }

        val textPaint = Paint().apply {
            color = textColor
            textSize = textSizeValue
            textAlign = Paint.Align.CENTER
        }

        this?.drawText(
            textData,
            this.width / 2f,
            this.height / 2 - (textPaint.descent() + textPaint.ascent()) / 2f,
            textPaint
        )
    }

    private fun Canvas?.drawCircleBackground() {
        backgroundProgress.setupDimens(width.toFloat(), height.toFloat())
        backgroundProgress.initialBackground(DEFAULT_CIRCLE_PADDING)
        backgroundProgress.setStrokeWidth(circleBackgroundSize)

        val backgroundColor = when (isProgressMin) {
            true -> circularDisableColor
            else -> circularBackgroundColor
        }

        backgroundProgress.setColor(backgroundColor)

        this?.drawArc(
            backgroundProgress.getBackground(),
            DEFAULT_ANGLE,
            DEFAULT_CIRCLE,
            false,
            backgroundProgress.getPaint()
        )
    }

    private fun Canvas?.drawCircleProgress() {
        mainProgress.initialBackgroundWithBoundsCoordinates(DEFAULT_CIRCLE_PADDING)

        val backgroundColor = when (isProgressMax && circularMaxProcessBackgroundColor != 0) {
            true -> circularMaxProcessBackgroundColor
            else -> circularProcessBackgroundColor
        }

        mainProgress.setColor(backgroundColor)

        mainProgress.setStrokeWidth(circleMainBackgroundSize)
        mainProgress.enableEdge()

        this?.drawArc(
            mainProgress.getBackground(),
            DEFAULT_START_ANGLE,
            progressBarAngleValue,
            false,
            mainProgress.getPaint()
        )
    }

}