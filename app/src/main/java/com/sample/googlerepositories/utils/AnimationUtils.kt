package com.sample.googlerepositories.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.sample.googlerepositories.R

/**
 * object containing animation helper methods
 */
object AnimationUtils {

    /**
     * used to animate a view fade in 2000 millisecond with filling after
     *
     * @param view is view to animate
     * @param onAnimationStart is call back method for anim start
     * @param onAnimationEnd is call back method for anim end
     */
    fun animateFadeIn2000FillAfter(
        view: View,
        onAnimationStart: () -> Unit = {},
        onAnimationEnd: () -> Unit = {},
    ) {
        val myAnim = AnimationUtils.loadAnimation(
            view.context,
            R.anim.fade_in_2000
        )
        view.startAnimation(myAnim)

        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                onAnimationStart()
            }

            override fun onAnimationEnd(p0: Animation?) {
                onAnimationEnd()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
    }
}