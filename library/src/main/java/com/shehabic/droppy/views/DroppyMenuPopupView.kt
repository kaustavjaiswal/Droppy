package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ScrollView
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/6/15.
 */
class DroppyMenuPopupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.droppyPopupStyle
) : ScrollView(context, attrs, defStyleAttr) {

    init {
        val defaultDrawable = resources.getDrawable(R.drawable.default_popup_background)

        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuPopupView, defStyleAttr, 0)
        val background = a.getDrawable(R.styleable.DroppyMenuPopupView_android_background)
        val height = a.getLayoutDimension(
            R.styleable.DroppyMenuPopupView_android_layout_height,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val width = a.getLayoutDimension(
            R.styleable.DroppyMenuPopupView_android_layout_width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        var lp = layoutParams
        if (lp == null) {
            lp = ViewGroup.LayoutParams(width, height)
        } else {
            lp.width = width
            lp.height = height
        }
        this.layoutParams = lp

        if (background != null) {
            setBackgroundDrawable(background)
        } else {
            setBackgroundDrawable(defaultDrawable)
        }
        a.recycle()
    }
}
