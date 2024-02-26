package com.kcrimi.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.kcrimi.tooltipdialog.ToolTipDialog

import kotlinx.android.synthetic.main.activity_sample.*
/**
 * Copyright (c) $today.year.
 * Created by Kevin Crimi as part of the ToolTipDialog library published for free usage as determined by the Apache 2.0 license.
 * https://github.com/kcrimi/ToolTipDialog
 **/
class SampleActivity : AppCompatActivity() {

    private val buttonPointUp by lazy { button_pointing_up }
    private val buttonNoArrowTop by lazy { button_no_arrow_top }
    private val buttonNowArrowBottom by lazy { button_no_arrow_bottom }
    private val buttonPointDown by lazy { button_pointing_down }
    private val fabButton by lazy { sample_fab_button }
    private val shadeToggle by lazy { enabled_shade_toggle }
    private var tooltipsClicked = 0

    private val toolTipListener = object: ToolTipDialog.ToolTipListener {
        override fun onClickToolTip() {
            tooltipsClicked++
            Toast.makeText(this@SampleActivity, "You've clicked $tooltipsClicked tooltips",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        setSupportActionBar(toolbar)

        setupButtons()
    }

    private fun setupButtons() {
        buttonPointUp.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            val dialog =ToolTipDialog(this, this)
                .title("Dialog can point up!")
                .pointTo(location[0] + it.width / 2, location[1] + it.height)
                .content("This is pointing up to the button you just clicked")
                .subtitle("Tooltip with arrow")
                .setToolTipListener(toolTipListener)

            if (shadeToggle.isChecked) {
                dialog.addPeekThroughView(it)
            }

            dialog.show()
        }

        buttonNoArrowTop.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            val dialog = ToolTipDialog(this, this)
                .title("Dialog can appear below!")
                .setYPosition(location[1] + it.height)
                .content("This is underneath the button you just clicked")
                .subtitle("Tooltip without arrow")
                .setToolTipListener(toolTipListener)

            if (shadeToggle.isChecked) {
                dialog.addPeekThroughView(it)
            }

            dialog.show()
        }

        buttonNowArrowBottom.setOnClickListener {
            val dialog = ToolTipDialog(this, this)
                .title("Dialog with Default location")
                .content("When you don't set any location for the dialog it presents on top by default")
                .subtitle("Default Tooltip")
                .setToolTipListener(toolTipListener)

            if (shadeToggle.isChecked) {
                dialog.addPeekThroughView(it)
            }

            dialog.show()
        }

        buttonPointDown.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            // Pass in a theme in order to style the dialog
            val dialog = ToolTipDialog(this, this, R.style.TooltipDialogTheme_Colorful)
                .title("Dialog can point down!")
                .pointTo(location[0] + it.width / 2, location[1] - it.height)
                .content("This is pointing down to the button you just clicked")
                .subtitle("Tooltip with arrow")
                .setToolTipListener(toolTipListener)

            if (shadeToggle.isChecked) {
                dialog.addPeekThroughView(it)
            }

            dialog.show()
        }

        fabButton.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            val dialog = ToolTipDialog(this, this)
                .title("You can point to any of your views on screen just like this FAB")
                .pointTo(location[0] + it.width / 2, location[1] - it.height)
                .setToolTipListener(toolTipListener)
            if (shadeToggle.isChecked) {
                dialog.addPeekThroughView(it)
            }

            dialog.show()
        }
    }
}
