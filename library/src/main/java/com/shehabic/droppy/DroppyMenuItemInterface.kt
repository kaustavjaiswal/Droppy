package com.shehabic.droppy

import android.content.Context
import android.view.View

/**
 * Created by shehabic on 2/28/15.
 */
interface DroppyMenuItemInterface {
    /**
     * Get type of droppy_menu item (e.g. droppy_menu, droppy_separator .... more type to be defined later)
     *
     * @return String
     */
    val type: Int

    /**
     * @return int
     */
    val id: Int

    /**
     * @return boolean
     */
    val isClickable: Boolean

    /**
     * @return View
     */
    val renderedView: View?

    /**
     * @param context
     * @return View rendered/inflated renderedView
     */
    fun render(context: Context): View

    /**
     * @param id Ideally the position in the list
     * @return DroppyMenuItemInterface
     */
    fun setId(id: Int): DroppyMenuItemInterface

    /**
     * @param isClickable
     * @return DroppyMenuItemInterface
     */
    fun setClickable(isClickable: Boolean): DroppyMenuItemInterface
}
