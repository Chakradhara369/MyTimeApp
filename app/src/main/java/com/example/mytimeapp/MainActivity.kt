package com.example.mytimeapp

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var clockText: TextView
    private lateinit var dateText: TextView

    private val updateClock = object : Runnable {
        override fun run() {
            val now = Date()

            clockText.text = SimpleDateFormat(
                "HH:mm:ss",
                Locale.getDefault()
            ).format(now)

            dateText.text = SimpleDateFormat(
                "EEEE, dd MMMM yyyy",
                Locale.getDefault()
            ).format(now)

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(12, 12, 20)
        window.navigationBarColor = Color.rgb(12, 12, 20)
        window.decorView.systemUiVisibility = 0

        val background = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(12, 12, 20))
        }

        val title = TextView(this).apply {
            text = "MY TIME"
            textSize = 18f
            letterSpacing = 0.3f
            setTextColor(Color.rgb(150, 150, 180))
            gravity = Gravity.CENTER
        }

        clockText = TextView(this).apply {
            text = "00:00:00"
            textSize = 48f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            typeface = android.graphics.Typeface.create(
                "sans-serif-light",
                android.graphics.Typeface.NORMAL
            )
            includeFontPadding = false
        }

        dateText = TextView(this).apply {
            textSize = 16f
            setTextColor(Color.rgb(170, 170, 190))
            gravity = Gravity.CENTER
        }

        val footer = TextView(this).apply {
            text = "EVERY SECOND COUNTS."
            textSize = 11f
            letterSpacing = 0.2f
            setTextColor(Color.rgb(110, 110, 140))
            gravity = Gravity.CENTER
        }

        background.addView(title)
        background.addView(
            clockText,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 36
            }
        )
        background.addView(
            dateText,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 20
            }
        )
        background.addView(
            footer,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 64
            }
        )

        setContentView(background)
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacks(updateClock)
        handler.post(updateClock)
    }

    override fun onPause() {
        handler.removeCallbacks(updateClock)
        super.onPause()
    }
}
