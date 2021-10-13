package com.fabian.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fabian.timer.flip.FlipDigit


class MainActivity : AppCompatActivity() {

    private lateinit var hours1:FlipDigit
    private lateinit var hours2:FlipDigit
    private lateinit var minutes1:FlipDigit
    private lateinit var minutes2:FlipDigit

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        val receiver = TimeChangerReceiver()
        registerReceiver(receiver, filter)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hours1 = findViewById(R.id.hours_1)
        hours2 = findViewById(R.id.hours_2)
        minutes1 = findViewById(R.id.minutes_1)
        minutes2 = findViewById(R.id.minutes_2)

        update()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun update(){
        val c: Calendar = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY).toString()
        if (hour.length == 1){
            hours1.setDigit(0, true)
            hours2.setDigit(Integer.parseInt(hour), true)
        }else{
            hours1.setDigit(Integer.parseInt(hour[0].toString()), true)
            hours2.setDigit(Integer.parseInt(hour[1].toString()), true)
        }
        val minute = c.get(Calendar.MINUTE).toString()
        if (minute.length == 1){
            minutes1.setDigit(0, true)
            minutes2.setDigit(Integer.parseInt(minute), true)
        }else{
            minutes1.setDigit(Integer.parseInt(minute[0].toString()), true)
            minutes2.setDigit(Integer.parseInt(minute[1].toString()), true)
        }

//        for(index in 0..9){
//            Thread.sleep(5000)
//            hours1.setDigit(index, true)
//            hours2.setDigit(index, true)
//            minutes1.setDigit(index, true)
//            minutes1.setDigit(index, true)
//        }
    }

    inner class TimeChangerReceiver:BroadcastReceiver(){
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onReceive(context: Context?, intent: Intent?) {
            update()
        }
    }


}