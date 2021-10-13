package com.fabian.timer.flip

import android.content.ContentValues
import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.fabian.timer.R
import java.lang.Exception

class Flip(
    act: Context?,
    private var id: Int,
    view: View,
    private var animComplete: OnAnimationComplete?,
    var isFast: Boolean,
    flipColor: Int
) : Animation.AnimationListener {

    private var flip_backup: ImageView? = null
    private var flip_backlow: ImageView? = null
    private var flip_frontup: ImageView? = null
    private var flip_frontlow: ImageView? = null

    private var context: Context? = act
    private var anim1: Animation? = null
    private var anim2: Animation? = null

    private var animTo = 0
    private var animFrom = 0

    var flipColor = 0

    interface OnAnimationComplete {
        fun onComplete(id: Int)
    }

    init {
        if (flipColor != 0) this.flipColor = flipColor
        flip_backup = view.findViewById<View>(R.id.image_flip_back_upper) as ImageView
        flip_backlow = view.findViewById<View>(R.id.image_flip_back_lower) as ImageView
        flip_frontup = view.findViewById<View>(R.id.image_flip_front_upper) as ImageView
        flip_frontlow = view.findViewById<View>(R.id.image_flip_front_lower) as ImageView
        setColor()
        init()
    }

    fun setDigit(digit: Int, withAnimation: Boolean) {
        var digit = digit
        if (digit < 0) digit = 0
        if (digit > 9) digit = 9
        animTo = digit
        Log.d(ContentValues.TAG, "setDigit: $digit")
        if (withAnimation) animateDigit(true) else setDigitImageToAll(digit)
    }

    private fun setColor() {
        if (flipColor != 0) {
            flip_backup!!.setColorFilter(flipColor, PorterDuff.Mode.SRC_IN)
            flip_backlow!!.setColorFilter(flipColor, PorterDuff.Mode.SRC_IN)
            flip_frontup!!.setColorFilter(flipColor, PorterDuff.Mode.SRC_IN)
            flip_frontlow!!.setColorFilter(flipColor, PorterDuff.Mode.SRC_IN)
        }
    }


    fun setFastFlip(isFast: Boolean) {
        this.isFast = isFast
    }

    fun setColor(flipColor: Int) {
        this.flipColor = flipColor
        setColor()
    }

    private fun animateDigit(isUpper: Boolean) {
        animFrom = getLastDigit(isUpper)
        startAnimation()
    }

    private fun init() {
        flip_backup!!.tag = 0
        flip_backlow!!.tag = 0
        flip_frontup!!.tag = 0
        flip_frontlow!!.tag = 0
        anim1 = AnimationUtils.loadAnimation(context, R.anim.flip_point_to_middle)
        anim1?.setAnimationListener(this)
        anim2 = AnimationUtils.loadAnimation(context, R.anim.flip_point_from_middle)
        anim2?.setAnimationListener(this)
    }

    private fun startAnimation() {
        if (animTo == animFrom) {
            animComplete?.onComplete(id)
        } else {
            startDigitAnimation(true)
        }
    }

    private fun startDigitAnimation(isUpper: Boolean) {
        if (isUpper) {
            flip_frontup!!.clearAnimation()
            flip_frontup!!.animation = anim1
            flip_frontup!!.startAnimation(anim1)
        } else {
            flip_frontlow!!.clearAnimation()
            flip_frontlow!!.animation = anim2
            flip_frontlow!!.startAnimation(anim2)
        }
    }

    private fun incrementFromCode() {
        animFrom++
        if (animFrom < 0) animFrom = 0
        if (animFrom > 9) animFrom = 9
    }

    private fun getLastDigit(isUpper: Boolean): Int {
        var digit = 0
        try {
            digit = if (isUpper) flip_frontup!!.tag as Int else flip_frontlow!!.tag as Int
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (digit > 9) digit = 0
        return digit
    }

    private fun getDigitToShow(): Int {
        return if (!isFast) if (animFrom + 1 > 9) 0 else animFrom + 1 else animTo
    }

    private fun setDigitImageToAll(digit: Int) {
        setDigitImage(digit, true, flip_backup!!)
        setDigitImage(digit, false, flip_backlow!!)
        setDigitImage(digit, true, flip_frontup!!)
        setDigitImage(digit, false, flip_frontlow!!)
    }

    private fun setDigitImage(digitToShow: Int, isUpper: Boolean, image: ImageView) {
        image.tag = digitToShow
        var resource = 0
        when (digitToShow) {
            0 -> resource = if (isUpper) R.drawable.ic_upper_0 else R.drawable.ic_lower_0
            1 -> resource = if (isUpper) R.drawable.ic_upper_1 else R.drawable.ic_lower_1
            2 -> resource = if (isUpper) R.drawable.ic_upper_2 else R.drawable.ic_lower_2
            3 -> resource = if (isUpper) R.drawable.ic_upper_3 else R.drawable.ic_lower_3
            4 -> resource = if (isUpper) R.drawable.ic_upper_4 else R.drawable.ic_lower_4
            5 -> resource = if (isUpper) R.drawable.ic_upper_5 else R.drawable.ic_lower_5
            6 -> resource = if (isUpper) R.drawable.ic_upper_6 else R.drawable.ic_lower_6
            7 -> resource = if (isUpper) R.drawable.ic_upper_7 else R.drawable.ic_lower_7
            8 -> resource = if (isUpper) R.drawable.ic_upper_8 else R.drawable.ic_lower_8
            9 -> resource = if (isUpper) R.drawable.ic_upper_9 else R.drawable.ic_lower_9
        }
        image.setImageResource(resource)
    }

    override fun onAnimationEnd(animation: Animation) {
        if (animation === anim1) {
            flip_frontup!!.visibility = View.INVISIBLE
            startDigitAnimation(false)
        } else if (animation === anim2) {
            flip_frontup!!.visibility = View.VISIBLE
            setDigitImage(getDigitToShow(), true, flip_frontup!!)
            setDigitImage(getDigitToShow(), false, flip_backlow!!)
            incrementFromCode()
            animateDigit(false)
        }
    }

    override fun onAnimationRepeat(arg0: Animation?) {}

    override fun onAnimationStart(animation: Animation) {
        if (animation === anim1) {
            flip_frontlow!!.visibility = View.INVISIBLE
            flip_frontup!!.visibility = View.VISIBLE
            setDigitImage(getDigitToShow(), false, flip_frontlow!!)
            setDigitImage(getDigitToShow(), true, flip_backup!!)
        } else if (animation === anim2) {
            flip_frontlow!!.visibility = View.VISIBLE
        }
    }

}