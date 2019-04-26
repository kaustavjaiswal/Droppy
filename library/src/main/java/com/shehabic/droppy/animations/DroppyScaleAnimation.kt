package com.shehabic.droppy.animations

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.views.DroppyMenuPopupView

class DroppyScaleAnimation : DroppyAnimation {

    override fun animateHide(popup: DroppyMenuPopup, popupView: DroppyMenuPopupView, anchor: View, itemSelected: Boolean) {
        val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.25f, Animation.RELATIVE_TO_SELF, 1f)
        scaleAnimation.duration = ANIMATION_DURATION.toLong()
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                popup.hideAnimationCompleted(itemSelected)
            }

        })
        popupView.startAnimation(scaleAnimation)
    }

    override fun animateShow(popup: DroppyMenuPopupView, anchor: View) {
        val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.25f, Animation.RELATIVE_TO_SELF, 1f)
        scaleAnimation.duration = ANIMATION_DURATION.toLong()
        scaleAnimation.fillAfter = true
        popup.startAnimation(scaleAnimation)
    }

    companion object {

        private const val ANIMATION_DURATION = 200
    }
}
