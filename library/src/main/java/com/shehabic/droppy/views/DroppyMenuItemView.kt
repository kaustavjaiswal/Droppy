package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/7/15.
 */
class DroppyMenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.droppyMenuItemStyle
) : LinearLayout(context, attrs, defStyleAttr) {

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuItemView, defStyleAttr, 0)

        val defaultDrawable = resources.getDrawable(R.drawable.default_menu_item_background)
        val defaultMinWidth = resources.getDimension(R.dimen.default_menu_item_minWidth)
        val defaultMinHeight = resources.getDimension(R.dimen.default_menu_item_minHeight)
        val defaultIsClickable = resources.getBoolean(R.bool.default_menu_item_clickable)

        val minWidth = a.getDimension(R.styleable.DroppyMenuItemView_android_minWidth, defaultMinWidth)
        val minHeight = a.getDimension(R.styleable.DroppyMenuItemView_android_minHeight, defaultMinHeight)

        var lp = layoutParams
        minimumWidth = minWidth.toInt()
        minimumHeight = minHeight.toInt()
        val width = a.getLayoutDimension(
            R.styleable.DroppyMenuItemView_android_layout_width,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val height = a.getLayoutDimension(
            R.styleable.DroppyMenuItemView_android_layout_height,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (lp == null) {
            lp = ViewGroup.LayoutParams(width, height)
        } else {
            lp.width = width
            lp.height = height
        }
        isClickable = a.getBoolean(R.styleable.DroppyMenuItemView_android_clickable, defaultIsClickable)
        orientation = LinearLayout.HORIZONTAL
        gravity = a.getInteger(R.styleable.DroppyMenuItemView_android_gravity, Gravity.CENTER_VERTICAL)
        val paddingTop = a.getDimension(
            R.styleable.DroppyMenuItemView_android_paddingTop,
            (resources.getDimension(R.dimen.default_menu_item_paddingTop).toInt()).toFloat()
        ).toInt()
        val paddingBottom = a.getDimension(
            R.styleable.DroppyMenuItemView_android_paddingBottom,
            (resources.getDimension(R.dimen.default_menu_item_paddingBottom).toInt()).toFloat()
        ).toInt()
        val paddingLeft = a.getDimension(
            R.styleable.DroppyMenuItemView_android_paddingLeft,
            (resources.getDimension(R.dimen.default_menu_item_paddingLeft).toInt()).toFloat()
        ).toInt()
        val paddingRight = a.getDimension(
            R.styleable.DroppyMenuItemView_android_paddingRight,
            (resources.getDimension(R.dimen.default_menu_item_paddingRight).toInt()).toFloat()
        ).toInt()
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        val background = a.getDrawable(R.styleable.DroppyMenuItemView_android_background)
        if (background != null) {
            setBackground(background)
        } else {
            setBackground(defaultDrawable)
        }

        this.layoutParams = lp
        a.recycle()
    }
}
