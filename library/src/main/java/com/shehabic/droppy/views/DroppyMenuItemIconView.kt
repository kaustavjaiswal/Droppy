package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/7/15.
 */
class DroppyMenuItemIconView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.droppyMenuItemIconStyle) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {

        val defaultMaxWidth = resources.getDimension(R.dimen.default_menu_item_icon_maxWidth).toInt()
        val defaultMaxHeight = resources.getDimension(R.dimen.default_menu_item_icon_maxHeight).toInt()
        val defaultWeight = 0f
        val defaultLayoutGravity = Gravity.START or Gravity.CENTER_VERTICAL
        val defaultMarginLeft = resources.getDimension(R.dimen.default_menu_item_icon_marginLeft).toInt()
        val defaultMarginRight = resources.getDimension(R.dimen.default_menu_item_icon_marginRight).toInt()

        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuItemIconView, defStyleAttr, 0)
        val maxWidth = a.getDimension(R.styleable.DroppyMenuItemIconView_android_maxWidth, defaultMaxWidth.toFloat()).toInt()
        val maxHeight = a.getDimension(R.styleable.DroppyMenuItemIconView_android_maxHeight, defaultMaxHeight.toFloat()).toInt()
        val width = a.getLayoutDimension(R.styleable.DroppyMenuItemIconView_android_layout_width, ViewGroup.LayoutParams.WRAP_CONTENT)
        val height = a.getLayoutDimension(R.styleable.DroppyMenuItemIconView_android_layout_height, ViewGroup.LayoutParams.WRAP_CONTENT)

        val lp = LinearLayout.LayoutParams(width, height)
        lp.rightMargin = a.getDimensionPixelSize(R.styleable.DroppyMenuItemIconView_android_layout_marginRight, defaultMarginRight)
        lp.leftMargin = a.getDimensionPixelSize(R.styleable.DroppyMenuItemIconView_android_layout_marginLeft, defaultMarginLeft)
        lp.width = width
        lp.height = height
        lp.weight = a.getFloat(R.styleable.DroppyMenuItemIconView_android_layout_weight, defaultWeight)
        lp.gravity = a.getInteger(R.styleable.DroppyMenuItemIconView_android_layout_gravity, defaultLayoutGravity)

        setMaxHeight(maxWidth)
        setMaxHeight(maxHeight)
        layoutParams = lp
        a.recycle()
    }
}
