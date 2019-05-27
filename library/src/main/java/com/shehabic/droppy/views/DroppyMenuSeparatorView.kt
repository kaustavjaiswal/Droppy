package com.shehabic.droppy.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shehabic.droppy.R

/**
 * Created by shehabic on 3/8/15.
 */
class DroppyMenuSeparatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.droppyMenuSeparatorStyle
) : LinearLayout(context, attrs, defStyleAttr) {

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.DroppyMenuSeparatorView, defStyleAttr, 0)

        val defaultSeparatorBackground = resources.getDrawable(R.drawable.droppy_separator_background)
        val defaultHeight = resources.getDimensionPixelSize(R.dimen.default_menu_separator_height)
        var lp = layoutParams
        val width = a.getLayoutDimension(
            R.styleable.DroppyMenuSeparatorView_android_layout_width,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val height = a.getLayoutDimension(R.styleable.DroppyMenuSeparatorView_android_layout_height, defaultHeight)
        if (lp == null) {
            lp = ViewGroup.LayoutParams(width, height)
        } else {
            lp.width = width
            lp.height = height
        }
        orientation = LinearLayout.HORIZONTAL

        val background = a.getDrawable(R.styleable.DroppyMenuSeparatorView_android_background)
        if (background != null) {
            setBackground(background)
        } else {
            setBackground(defaultSeparatorBackground)
        }

        this.layoutParams = lp
        a.recycle()
    }
}
