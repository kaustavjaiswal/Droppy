package com.shehabic.droppy_samples

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shehabic.droppy.DroppyClickCallbackInterface
import com.shehabic.droppy.DroppyMenuCustomItem
import com.shehabic.droppy.DroppyMenuItem
import com.shehabic.droppy.DroppyMenuPopup

class ActivityWithFragment : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_with_fragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, PlaceholderFragment())
                    .commit()
        }
    }


    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_activity_with, menu)
        return true
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


    /**
     * A placeholder fragment containing a simple renderedView.
     */
    open class PlaceholderFragment : Fragment() {

        internal lateinit var droppyMenu: DroppyMenuPopup
        internal lateinit var btn: Button
        internal var seekbarValue = 0

        override
        fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_activity_with, container, false)
            initButtons(rootView)
            return rootView
        }

        fun initButtons(rootView: View) {
            btn = rootView.findViewById(R.id.menuButton) as Button
            btn.setOnClickListener {
                initDroppyMenu(btn)
                showDroppyMenu()
            }
        }

        private fun initDroppyMenu(btn: Button) {
            val droppyBuilder = DroppyMenuPopup.Builder(btn.context, btn)
            droppyBuilder.addMenuItem(DroppyMenuItem("test1"))
                    .addMenuItem(DroppyMenuItem("test2"))
                    .addSeparator()
                    .addMenuItem(DroppyMenuItem("test3", R.drawable.ic_launcher))
                    .triggerOnAnchorClick(false)

            val sBarItem = DroppyMenuCustomItem(R.layout.slider)
            droppyBuilder.addMenuItem(sBarItem)

            droppyBuilder.setOnClick(object : DroppyClickCallbackInterface {
                override
                fun call(v: View, id: Int) {
                    Log.d("Clicked on ", id.toString())
                }
            })

            droppyMenu = droppyBuilder.build()
        }

        protected fun showDroppyMenu() {
            droppyMenu.show()
            val sBar = droppyMenu.menuView?.findViewById(R.id.seekBar1) as SeekBar
            sBar.progress = seekbarValue
            sBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override
                fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    seekbarValue = progress
                }

                override
                fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override
                fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
        }
    }
}
