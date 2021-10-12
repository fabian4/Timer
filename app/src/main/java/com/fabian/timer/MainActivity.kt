package com.fabian.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var hours:TextView
    private lateinit var minutes:TextView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        val receiver = TimeChangerReceiver()
        registerReceiver(receiver, filter)

        //屏幕常亮设置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hours = findViewById(R.id.hours)
        minutes = findViewById(R.id.minutes)

        val c: Calendar = Calendar.getInstance()
        hours.text = c.get(Calendar.HOUR_OF_DAY).toString().fix()
        minutes.text = c.get(Calendar.MINUTE).toString().fix()
    }

    fun String.fix():String{
        if(this.length == 1){
            return "0$this"
        }
        return this
    }

    inner class TimeChangerReceiver:BroadcastReceiver(){
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onReceive(context: Context?, intent: Intent?) {
            val c: Calendar = Calendar.getInstance()
            Log.d("receiver", c.get(Calendar.HOUR_OF_DAY).toString()+c.get(Calendar.MINUTE).toString())
            hours.text = c.get(Calendar.HOUR_OF_DAY).toString().fix()
            minutes.text = c.get(Calendar.MINUTE).toString().fix()
        }
    }


}