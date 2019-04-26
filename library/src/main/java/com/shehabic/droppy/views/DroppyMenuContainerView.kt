package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/6/15.
 */
class DroppyMenuContainerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.droppyMenuStyle) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        this.orientation = VERTICAL
        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuPopupView, defStyleAttr, 0)
        var lp = layoutParams
        val height = a.getLayoutDimension(R.styleable.DroppyMenuContainerView_android_layout_height, ViewGroup.LayoutParams.WRAP_CONTENT)
        val width = a.getLayoutDimension(R.styleable.DroppyMenuContainerView_android_layout_width, ViewGroup.LayoutParams.WRAP_CONTENT)

        if (lp == null) {
            lp = ViewGroup.LayoutParams(width, height)
        } else {
            lp.width = width
            lp.height = height
        }
        layoutParams = lp
        a.recycle()
    }
}
