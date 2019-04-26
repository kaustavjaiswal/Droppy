package com.shehabic.droppy

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.shehabic.droppy.model.FontObject
import com.shehabic.droppy.model.globalFont

import com.shehabic.droppy.views.DroppyMenuItemIconView
import com.shehabic.droppy.views.DroppyMenuItemTitleView
import com.shehabic.droppy.views.DroppyMenuItemView

/**
 * Created by shehabic on 2/28/15.
 */
open class DroppyMenuItem : DroppyMenuItemAbstract {

    private var iconDrawable: Drawable? = null
    override var renderedView: View? = null

    fun initMenuItem(title: String, iconResourceId: Int) {
        this.title = title
        if (iconResourceId > 0) {
            this.icon = iconResourceId
        }
    }

    constructor(title: String) {
        initMenuItem(title, -1)
    }

    constructor(title: String, iconResourceId: Int) {
        initMenuItem(title, iconResourceId)

    }

    fun setIcon(iconDrawable: Drawable) {
        this.iconDrawable = iconDrawable
    }

    override fun render(context: Context): View {

        renderedView = DroppyMenuItemView(context)

        if (this.icon != -1) {
            val droppyMenuItemIcon = DroppyMenuItemIconView(context)
            droppyMenuItemIcon.setImageResource(this.icon)
            (renderedView as DroppyMenuItemView).addView(droppyMenuItemIcon)
        } else if (this.iconDrawable != null) {
            val droppyMenuItemIcon = DroppyMenuItemIconView(context)
            droppyMenuItemIcon.setImageDrawable(iconDrawable)
            (renderedView as DroppyMenuItemView).addView(droppyMenuItemIcon)
        }

        val droppyMenuItemTitle = DroppyMenuItemTitleView(context)
        droppyMenuItemTitle.text = this.title
        globalFont?.let {
            droppyMenuItemTitle.customizeTextView(context, it)
        }
        (renderedView as DroppyMenuItemView).addView(droppyMenuItemTitle)

        return (renderedView as DroppyMenuItemView)
    }

    fun TextView.customizeTextView(context: Context, fontObject: FontObject) {
        if (fontObject.typeface.isNotBlank()) {
            typeface = context.loadTypeface(fontObject.typeface)
        }
        if (fontObject.size != 0) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontObject.size.toFloat())
        }
    }

    private fun Context.loadTypeface(fontName: String): Typeface {
        val typeface: Typeface
        typeface = try {
            Typeface.createFromAsset(assets, "fonts/$fontName.ttf")
        } catch (e: RuntimeException) {
            Log.e("TypeFaceError", e.toString())
            Typeface.DEFAULT
        }
        return typeface
    }
}
