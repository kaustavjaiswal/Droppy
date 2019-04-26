package com.shehabic.droppy.animations

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.views.DroppyMenuPopupView

class DroppyFadeInAnimation : DroppyAnimation {

    override fun animateHide(popup: DroppyMenuPopup, popupView: DroppyMenuPopupView, anchor: View, itemSelected: Boolean) {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = ANIMATION_DURATION.toLong()
        alphaAnimation.fillAfter = true
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                popup.hideAnimationCompleted(itemSelected)
            }

        })
        popupView.startAnimation(alphaAnimation)
    }

    override fun animateShow(popup: DroppyMenuPopupView, anchor: View) {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = ANIMATION_DURATION.toLong()
        alphaAnimation.fillAfter = true
        popup.startAnimation(alphaAnimation)
    }

    companion object {

        private const val ANIMATION_DURATION = 200
    }
}
