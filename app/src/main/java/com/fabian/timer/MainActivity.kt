package com.fabian.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fabian.timer.flip.FlipDigit


class MainActivity : AppCompatActivity() {

    private lateinit var hours:TextView
    private lateinit var minutes:TextView
    private lateinit var filipSingle:FlipDigit

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val filter = IntentFilter()
//        filter.addAction(Intent.ACTION_TIME_TICK)
//        val receiver = TimeChangerReceiver()
//        registerReceiver(receiver, filter)

        //屏幕常亮设置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        hours = findViewById(R.id.hours)
//        minutes = findViewById(R.id.minutes)
//
//        val c: Calendar = Calendar.getInstance()
//        hours.text = c.get(Calendar.HOUR_OF_DAY).toString().fix()
//        minutes.text = c.get(Calendar.MINUTE).toString().fix()

        filipSingle = findViewById(R.id.filipSingle)
        filipSingle.setDigit(3,true)
        Thread {
            for (index in 0..9){
                Thread.sleep(5000)
                filipSingle.setDigit(index, true)
            }
        }.start()
    }

//    fun String.fix():String{
//        if(this.length == 1){
//            return "0$this"
//        }
//        return this
//    }
//
//    inner class TimeChangerReceiver:BroadcastReceiver(){
//        @RequiresApi(Build.VERSION_CODES.N)
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val c: Calendar = Calendar.getInstance()
//            hours.text = c.get(Calendar.HOUR_OF_DAY).toString().fix()
//            minutes.text = c.get(Calendar.MINUTE).toString().fix()
//        }
//    }


}