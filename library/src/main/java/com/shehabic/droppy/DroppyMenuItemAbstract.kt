package com.shehabic.droppy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by shehabic on 2/28/15.
 */
abstract class DroppyMenuItemAbstract : DroppyMenuItemInterface {

    protected var title: String? = null
    protected var icon = -1
    override var type = TYPE_MENU_ITEM
    override var renderedView: View? = null
    protected var customViewResourceId = -1
    override var id = -1
    override var isClickable = true

    override fun render(context: Context): View {
        if (this.renderedView == null) {
            this.renderedView = LayoutInflater.from(context).inflate(this.customViewResourceId, null)
        }
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        renderedView!!.layoutParams = lp

        return renderedView as View
    }

    override fun setId(id: Int): DroppyMenuItemInterface {
        this.id = id

        return this
    }

    override fun setClickable(isClickable: Boolean): DroppyMenuItemInterface {
        this.isClickable = isClickable

        return this
    }

    companion object {
        const val TYPE_MENU_ITEM = 0
        const val TYPE_CUSTOM = 1
        const val TYPE_MENU_SEPARATOR = 2
    }
}
