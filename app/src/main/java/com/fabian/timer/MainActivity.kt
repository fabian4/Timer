package com.fabian.timer

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val timer = findViewById<TextView>(R.id.timer)
//        timer.typeface = Typeface.createFromAsset(assets, "fonts/ds_digi.ttf")
//        timer.text = "23:22"
//        timer.textSize = 250f
    }
}