package com.shehabic.droppy.animations

import android.view.View

import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.views.DroppyMenuPopupView

interface DroppyAnimation {

    fun animateShow(popup: DroppyMenuPopupView, anchor: View)

    fun animateHide(popup: DroppyMenuPopup, popupView: DroppyMenuPopupView, anchor: View, itemSelected: Boolean)
}
