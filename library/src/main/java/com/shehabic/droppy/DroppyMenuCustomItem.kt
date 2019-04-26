package com.shehabic.droppy

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * Created by shehabic on 3/1/15.
 */
class DroppyMenuCustomItem : DroppyMenuItemAbstract {

    constructor(customResourceId: Int) {
        isClickable = false
        type = TYPE_CUSTOM
        customViewResourceId = customResourceId
    }

    constructor(customView: View) {
        isClickable = false
        type = TYPE_CUSTOM
        renderedView = customView
    }

    override fun render(context: Context): View {
        if (renderedView == null) {
            renderedView = LayoutInflater.from(context).inflate(customViewResourceId, null)
        }
        return renderedView as View
    }
}
