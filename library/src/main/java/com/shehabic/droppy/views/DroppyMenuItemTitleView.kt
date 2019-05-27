package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/7/15.
 */
class DroppyMenuItemTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.droppyMenuItemTitleStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {

        val defaultWidth = ViewGroup.LayoutParams.MATCH_PARENT
        val defaultMinWidth = resources.getDimension(R.dimen.default_menu_item_title_minWidth).toInt()
        val defaultMinHeight = resources.getDimension(R.dimen.default_menu_item_title_minHeight).toInt()
        val defaultWeight = 1f
        val defaultColor = resources.getColor(R.color.default_menu_item_title_textColor)
        val defaultGravity = Gravity.CENTER_VERTICAL
        val defaultLayoutGravity = Gravity.END or Gravity.CENTER_VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuItemTitleView, defStyleAttr, 0)
        val minWidth =
            a.getDimension(R.styleable.DroppyMenuItemTitleView_android_minWidth, defaultMinWidth.toFloat()).toInt()
        val minHeight =
            a.getDimension(R.styleable.DroppyMenuItemTitleView_android_minHeight, defaultMinHeight.toFloat()).toInt()
        val width = a.getLayoutDimension(R.styleable.DroppyMenuItemTitleView_android_layout_width, defaultWidth)
        val height = a.getLayoutDimension(
            R.styleable.DroppyMenuItemTitleView_android_layout_height,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val color = a.getColor(R.styleable.DroppyMenuItemTitleView_android_textColor, defaultColor)

        gravity = a.getInt(R.styleable.DroppyMenuItemTitleView_android_gravity, defaultGravity)

        val lp = LinearLayout.LayoutParams(width, height)
        lp.width = width
        lp.height = height
        lp.weight = a.getFloat(R.styleable.DroppyMenuItemTitleView_android_layout_weight, defaultWeight)
        lp.gravity = a.getInteger(R.styleable.DroppyMenuItemTitleView_android_layout_gravity, defaultLayoutGravity)

        layoutParams = lp
        setMinHeight(minWidth)
        setMinHeight(minHeight)

        setTextColor(color)
        a.recycle()
    }
}
