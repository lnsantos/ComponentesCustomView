package com.lnsantos.customviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.lnsantos.circularprogress.CircularProgress

// CircularTextView -> https://betterprogramming.pub/draw-custom-views-in-android-a321fa157d60
class MainActivity : AppCompatActivity() {

    private val seekbar : AppCompatSeekBar by lazy { findViewById(R.id.seek_bar) }
    private val progress: CircularProgress by lazy { findViewById(R.id.progress_circular)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress.setProgress(p1.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
}