package com.shehabic.droppy

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Point
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.shehabic.droppy.animations.DroppyAnimation
import com.shehabic.droppy.model.FontObject
import com.shehabic.droppy.model.globalFont
import com.shehabic.droppy.views.DroppyMenuContainerView
import com.shehabic.droppy.views.DroppyMenuPopupView
import java.util.*

open class DroppyMenuPopup constructor(
        var mContext: Context,
        var anchor: View,
        menuItem: List<DroppyMenuItemInterface>,
        var droppyClickCallbackInterface: DroppyClickCallbackInterface?,
        addTriggerOnAnchorClick: Boolean,
        var popupMenuLayoutResourceId: Int,
        var mOnDismissCallback: OnDismissCallback?
) {
    var menuItems: List<DroppyMenuItemInterface> = ArrayList()
    var mContentView: View? = null
    lateinit var mPopupView: DroppyMenuPopupView
    lateinit var droppyMenuContainer: DroppyMenuContainerView
    var modalWindow: FrameLayout? = null
    var mPopupWidth: Int = 0
    var mPopupHeight: Int = 0
    var statusBarHeight = getStatusBarHeightValue()
    var offsetX: Int = 0
    var offsetY: Int = 0
    var popupAnimation: DroppyAnimation? = null
    val menuView: View?
        get() = mPopupView

    val screenSize: Point
        get() {
            val size = Point()
            getActivity(anchor.context)!!.windowManager.defaultDisplay.getSize(size)

            return size
        }

    val isTranslucentStatusBar: Boolean
        get() {
            val w = getActivity(anchor.context)!!.window
            val lp = w.attributes
            val flags = lp.flags

            return flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        }

    val anchorCoordinates: Point
        get() {
            val coords = IntArray(2)
            anchor.getLocationOnScreen(coords)

            return Point(coords[0], coords[1] - getStatusBarHeightValue())
        }

    interface OnDismissCallback {
        fun call()
    }

    init {
        this.menuItems = menuItem
        if (addTriggerOnAnchorClick) {
            anchor.setOnClickListener { show() }
        }
    }

    fun getMenuItemById(id: Int): DroppyMenuItemInterface? {
        for (menuItem in menuItems) {
            if (menuItem.id == id) {
                return menuItem
            }
        }

        return null
    }

    fun addModal() {
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        lp.leftMargin = 0
        lp.topMargin = 0
        modalWindow = FrameLayout(mContext)
        modalWindow!!.isClickable = true
        modalWindow!!.layoutParams = lp
        modalWindow!!.setOnClickListener { dismiss(false) }
        lp.topMargin -= getActivity(mContext)!!.window.decorView.top
        getActivity(mContext)!!.window.addContentView(modalWindow, lp)
    }

    fun show() {
        addModal()
        render()
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        adjustDropDownPosition(lp, offsetX, offsetY)
        mContentView = PopupViewContainer(mContext)
        detachPopupView()
        (mContentView as ViewGroup).addView(mPopupView)
        mContentView!!.isFocusable = true
        mContentView!!.isClickable = true
        getActivity(mContext)!!.window.addContentView(mContentView, lp)
        mContentView!!.requestFocus()
        if (popupAnimation != null) {
            popupAnimation!!.animateShow(mPopupView, anchor)
        }
    }

    fun detachPopupView() {
        if (mPopupView.parent != null) {
            try {
                (mPopupView.parent as ViewGroup).removeView(mPopupView)
            } catch (e: Exception) {

            }

        }
    }

    fun hideAnimationCompleted(itemSelected: Boolean) {
        dismissPopup(itemSelected)
    }

    fun dismiss(itemSelected: Boolean) {
        if (popupAnimation != null) {
            popupAnimation!!.animateHide(this, mPopupView, anchor, itemSelected)

            return
        }
        dismissPopup(itemSelected)
    }

    fun dismissPopup(itemSelected: Boolean) {
        if (mContentView != null && modalWindow != null) {
            (mContentView!!.parent as ViewGroup).removeView(mContentView)
            (modalWindow!!.parent as ViewGroup).removeView(modalWindow)

            if (!itemSelected && this.mOnDismissCallback != null) {
                mOnDismissCallback!!.call()
                this.mOnDismissCallback = null
            }
        }
    }

    @JvmOverloads
    fun render(forceRender: Boolean = false) {
        if (forceRender) {
            if ((mPopupView as ViewGroup).childCount > 0) {
                (mPopupView as ViewGroup).removeAllViews()
            }
            mPopupView = com.shehabic.droppy.views.DroppyMenuPopupView(mContext)
            droppyMenuContainer = DroppyMenuContainerView(mContext)
            mPopupView.addView(droppyMenuContainer)
            val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            mPopupView.layoutParams = lp
            mContentView = mPopupView
            var i = 0
            for (droppyMenuItem in this.menuItems) {
                addMenuItemView(droppyMenuItem, i)
                if (droppyMenuItem.isClickable) {
                    i++
                }
            }
        }
        mPopupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopupWidth = mPopupView.measuredWidth
        mPopupHeight = mPopupView.measuredHeight
    }

    fun callOnClick(v: View, id: Int) {
        if (this.droppyClickCallbackInterface != null) {
            droppyClickCallbackInterface!!.call(v, id)
            dismiss(true)
        }
    }

    fun addMenuItemView(menuItem: DroppyMenuItemInterface, id: Int) {
        val menuItemView = menuItem.render(mContext)

        if (menuItem.isClickable) {

            menuItemView.id = id
            if (menuItem.id == -1) {
                menuItem.setId(id)
            }
            val clickableItemId = menuItem.id

            menuItemView.setOnClickListener { v -> callOnClick(v, clickableItemId) }
        }
        droppyMenuContainer.addView(menuItemView)
    }

    fun getStatusBarHeightValue(): Int {
        if (statusBarHeight == -1 && isTranslucentStatusBar) {
            statusBarHeight = 0
        } else if (statusBarHeight == -1) {
            var result = 0
            val resourceId = anchor.context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = anchor.context.resources.getDimensionPixelSize(resourceId)
            }
            statusBarHeight = result
        }
        return statusBarHeight
    }

    fun setPopupAnimationValue(popupAnimation: DroppyAnimation?) {
        this.popupAnimation = popupAnimation
    }

    @SuppressLint("RtlHardcoded")
    fun adjustDropDownPosition(params: FrameLayout.LayoutParams, xOffset: Int, yOffset: Int) {
        val anchorPosition = anchorCoordinates
        var finalX = anchorPosition.x + xOffset
        val anchorHeight = anchor.getHeight()
        var finalY = anchorPosition.y + anchorHeight
        val screen = screenSize
        val rightMargin = screen.x - (finalX + mPopupView.measuredWidth)
        if (rightMargin < 0) {
            finalX = screen.x - (mPopupWidth + xOffset)
        }

        if (finalY + mPopupHeight > screen.y /*&& p.y > mPopupHeight*/) {
            finalY = anchorPosition.y - mPopupHeight - -1 * yOffset
        }

        params.leftMargin = Math.max(0, finalX)
        params.topMargin = Math.max(0, finalY)
        params.gravity = Gravity.LEFT or Gravity.TOP


        val maxDistanceAbove = anchorPosition.y
        val maxDistanceBelow = screen.y - anchorHeight - anchorPosition.y - offsetY
        val popupAboveAnchor = maxDistanceAbove > maxDistanceBelow
        val noRoomAbove = popupAboveAnchor && anchorPosition.y < mPopupHeight
        val noRoomBelow = !popupAboveAnchor && mPopupHeight > maxDistanceBelow
        if (noRoomAbove || noRoomBelow) {
            if (popupAboveAnchor) {
                params.height = maxDistanceAbove
                params.topMargin = 0
            } else {
                params.height = maxDistanceBelow
                params.topMargin = anchorHeight + anchorPosition.y
            }
        }
    }

    inner class PopupViewContainer(context: Context) : FrameLayout(context)

    class Builder(var ctx: Context, var parentMenuItem: View) {
        var menuItems = mutableListOf<DroppyMenuItemInterface>()
        lateinit var callbackInterface: DroppyClickCallbackInterface
        var triggerOnAnchorClick = true
        lateinit var onDismissCallback: OnDismissCallback
        var offsetX = -20
        var offsetY = 25
        lateinit var droppyAnimation: DroppyAnimation

        fun addMenuItem(droppyMenuItem: DroppyMenuItemInterface): Builder {
            menuItems.add(droppyMenuItem)

            return this
        }

        fun addSeparator(): Builder {
            menuItems.add(DroppyMenuSeparator())

            return this
        }

        fun addFont(font: FontObject): Builder {
            globalFont = font

            return this
        }

        fun setOnClick(droppyClickCallbackInterface1: DroppyClickCallbackInterface): Builder {
            callbackInterface = droppyClickCallbackInterface1

            return this
        }

        fun triggerOnAnchorClick(onAnchorClick: Boolean): Builder {
            triggerOnAnchorClick = onAnchorClick
            return this
        }

        fun setOnDismissCallback(onDismissCallback: OnDismissCallback): Builder {
            this.onDismissCallback = onDismissCallback
            return this
        }

        fun setPopupAnimation(droppyAnimation: DroppyAnimation): Builder {
            this.droppyAnimation = droppyAnimation
            return this
        }

        fun fromMenu(menuResourceId: Int): Builder {
            val menu = newMenuInstance(ctx)
            val menuInflater = MenuInflater(ctx)
            menuInflater.inflate(menuResourceId, menu)
            var lastGroupId = menu!!.getItem(0).groupId
            for (i in 0 until menu.size()) {
                val mItem = menu.getItem(i)
                val dMenuItem = DroppyMenuItem(mItem.title.toString())

                if (mItem.icon != null) {
                    dMenuItem.setIcon(mItem.icon)
                }

                if (mItem.itemId > 0) {
                    dMenuItem.setId(mItem.itemId)
                }

                if (mItem.groupId != lastGroupId) {
                    menuItems.add(DroppyMenuSeparator())
                    lastGroupId = mItem.groupId
                }

                menuItems.add(dMenuItem)
            }

            return this
        }

        fun newMenuInstance(context: Context): Menu? {
            try {
                val menuBuilderClass = Class.forName("com.android.internal.renderedView.menu.MenuBuilder")
                val constructor = menuBuilderClass.getDeclaredConstructor(Context::class.java)

                return constructor.newInstance(context) as Menu
            } catch (e: Exception) {

            }

            return null
        }

        fun build(): DroppyMenuPopup {
            val popup = DroppyMenuPopup(ctx, parentMenuItem, menuItems, callbackInterface, triggerOnAnchorClick, -1, onDismissCallback)
            popup.offsetX = offsetX
            popup.offsetY = offsetY
            popup.popupAnimation = droppyAnimation

            return popup
        }
    }

    companion object {

        fun getActivity(context: Context): Activity? {

            if (context is Activity) {
                return context
            } else if (context is ContextWrapper) {
                return getActivity(context.baseContext)
            }

            return null
        }
    }
}


