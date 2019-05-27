package com.shehabic.droppy

import android.content.Context
import android.view.View

/**
 * Created by shehabic on 2/28/15.
 */
class DroppyMenuSeparator : DroppyMenuItemAbstract() {

    init {
        type = TYPE_MENU_SEPARATOR
        setId(-1)
        isClickable = false
    }

    override fun render(context: Context): View {
        if (renderedView == null) {
            renderedView = com.shehabic.droppy.views.DroppyMenuSeparatorView(context)
        }

        return renderedView as View
    }
}
