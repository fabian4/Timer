package com.fabian.timer.flip

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.fabian.timer.R

class FlipDigit : RelativeLayout {

    private var color = 0
    private var FlipItem: View? = null

    private var isFast = false
    private val mCurrentDigit = 0

    private var flip: Flip? = null

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlipDigit)
        val num = typedArray.indexCount
        try {
            isFast = typedArray.getBoolean(R.styleable.FlipDigit_isFast, false)
            // Set tint
            for (i in 0 until num) {
                val attr = typedArray.getIndex(i)
                if (attr == R.styleable.FlipDigit_tintColor) color = typedArray.getColor(attr, 1)
            }
        } // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle()
        }
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
    }

    private fun inflateLayout() {
        val layoutInflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        FlipItem = layoutInflater.inflate(R.layout.view_flipmeter_spinner, this)
    }

    fun setDigit(animateTo: Int, withAnimation: Boolean) {
        flip!!.setDigit(animateTo, withAnimation)
    }


    private fun initialize() {
        inflateLayout()
        Log.d("Item", "initialize: $isFast")
        flip = FlipItem?.let { Flip(context, id, it, null, isFast, color) }
    }

    fun getCurrentDigit(): Int {
        return mCurrentDigit
    }

    fun setFastFlip(isFastFlip: Boolean) {
        flip!!.setFastFlip(isFastFlip)
    }

    fun setColor(color: Int) {
        flip!!.setColor(color)
    }


}