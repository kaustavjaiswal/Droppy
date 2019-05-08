package com.shehabic.droppy_samples

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import com.shehabic.droppy.DroppyClickCallbackInterface
import com.shehabic.droppy.DroppyMenuCustomItem
import com.shehabic.droppy.DroppyMenuItem
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.animations.DroppyFadeInAnimation
import com.shehabic.droppy.animations.DroppyScaleAnimation
import com.shehabic.droppy.model.FontObject

/**
 * Created by shehabic on 3/21/15.
 */
open class MainActivity : Activity(), DroppyMenuPopup.OnDismissCallback, DroppyClickCallbackInterface {

    internal lateinit var droppyMenu: DroppyMenuPopup
    internal lateinit var btn: Button
    internal lateinit var btn2: Button
    internal lateinit var btn3: Button

    protected var seekbarValue = 0

    private fun showDroppyMenu() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            initDroppyMenu(btn)
            showDroppyMenu()
        }

        btn2 = findViewById(R.id.button2)
        btn2.setOnClickListener {
            initDroppyMenu(btn2)
            showDroppyMenu()
        }

        btn3 = findViewById(R.id.button3)
        btn3.setOnClickListener { initDroppyMenuFromXml(btn3) }
    }

    private fun initDroppyMenu(btn: Button) {
        val droppyBuilder = DroppyMenuPopup.Builder(this, btn)
        droppyBuilder.addMenuItem(DroppyMenuItem("First Item"))
                .addMenuItem(DroppyMenuItem("Second Item"))
                .addSeparator()
                .setOnDismissCallback(this)
                .setOnClick(this)
                .addFont(FontObject("", "Raleway-Italic", 20))
                .addMenuItem(DroppyMenuItem("Third Item", R.drawable.ic_launcher))
                .setPopupAnimation(DroppyFadeInAnimation())
                .triggerOnAnchorClick(false)

        val sBarItem = DroppyMenuCustomItem(R.layout.slider)
        droppyBuilder.addMenuItem(sBarItem)
                .setOnClick(this)

        droppyMenu = droppyBuilder.build()
    }

    private fun initDroppyMenuFromXml(btn: Button) {
        val droppyBuilder = DroppyMenuPopup.Builder(this, btn)
        val droppyMenu = droppyBuilder.fromMenu(R.menu.droppy)
                .triggerOnAnchorClick(false)
                .setOnClick(this)
                .setOnDismissCallback(this)
                .setPopupAnimation(DroppyScaleAnimation())
                .build()
        droppyMenu.show()
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override
    fun call() {
        Toast.makeText(this, "Menu dismissed", Toast.LENGTH_SHORT).show()
    }

    override
    fun call(v: View, id: Int) {
        val idText: String

        when (id) {
            R.id.droppy1 -> idText = "Droppy Item 1"
            R.id.droppy2 -> idText = "Droppy Item 2"
            R.id.droppy3 -> idText = "Droppy Item 3"
            R.id.droppy4 -> idText = "Droppy Item 4"
            R.id.droppy5 -> idText = "Droppy Item 5"
            R.id.droppy6 -> idText = "Droppy Item 6"
            R.id.droppy7 -> idText = "Droppy Item 7"
            R.id.droppy8 -> idText = "Droppy Item 8"
            R.id.droppy9 -> idText = "Droppy Item 9"
            // .
            // .
            // .
            // .
            R.id.droppy18 -> idText = "Droppy Item 18"
            R.id.droppy19 -> idText = "Droppy Item 19"
            R.id.droppy20 -> idText = "Droppy Item 20"
            R.id.droppy21 -> idText = "Droppy Item 21"
            R.id.droppy22 -> idText = "Droppy Item 22"
            R.id.droppy23 -> idText = "Droppy Item 23"

            else -> idText = id.toString()
        }

        Toast.makeText(this, "Tapped on item with id: $idText", Toast.LENGTH_SHORT).show()
    }
}
