package com.shehabic.droppy_samples

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.shehabic.droppy.DroppyClickCallbackInterface
import com.shehabic.droppy.DroppyMenuCustomItem
import com.shehabic.droppy.DroppyMenuItem
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy_samples.util.SystemUiHider

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
open class FullscreenActivity : Activity() {

    private lateinit var droppyMenu: DroppyMenuPopup
    private lateinit var btn: Button
    internal var seekbarValue = 0

    /**
     * The instance of the [SystemUiHider] for this activity.
     */
    private var mSystemUiHider: SystemUiHider? = null


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private var mDelayHideTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override
        fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
            return false
        }
    }

    private var mHideHandler = Handler()
    private var mHideRunnable: Runnable = Runnable { mSystemUiHider!!.hide() }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        val controlsView = findViewById<LinearLayout>(R.id.fullscreen_content_controls)
        val contentView = findViewById<TextView>(R.id.fullscreen_content)

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS)
        mSystemUiHider!!.setup()
        mSystemUiHider!!
                .setOnVisibilityChangeListener(object : SystemUiHider.OnVisibilityChangeListener {
                    // Cached values.
                    var mControlsHeight: Int = 0
                    var mShortAnimTime: Int = 0

                    @SuppressLint("ObsoleteSdkInt")
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    override fun onVisibilityChange(visible: Boolean) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.height
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = resources.getInteger(
                                        android.R.integer.config_shortAnimTime)
                            }
                            controlsView.animate()
                                    .translationY(if (visible) 0F else mControlsHeight.toFloat()).duration = mShortAnimTime.toLong()
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.visibility = if (visible) View.VISIBLE else View.GONE
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS)
                        }
                    }
                })

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener {
            mSystemUiHider!!.toggle()
        }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById<Button>(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener)
        initButtons()
    }

    override fun onPostCreate(savedInstanceState: Bundle) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    private fun initButtons() {
        btn = findViewById<Button>(R.id.menuButton)
        btn.setOnClickListener {
            initDroppyMenu(btn)
            showDroppyMenu()
        }
    }

    private fun initDroppyMenu(btn: Button) {
        val droppyBuilder = DroppyMenuPopup.Builder(this, btn)
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

    private fun showDroppyMenu() {
        droppyMenu.show()
        val sBar = droppyMenu.menuView?.findViewById(R.id.seekBar1) as SeekBar
        sBar.setProgress(seekbarValue)
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

    companion object {

        /**
         * Whether or not the system UI should be auto-hidden after
         * [.AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [.AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * If set, will toggle the system UI visibility upon interaction. Otherwise,
         * will show the system UI visibility upon interaction.
         */
        private val TOGGLE_ON_CLICK = true

        /**
         * The flags to pass to [SystemUiHider.getInstance].
         */
        private val HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION
    }

}
