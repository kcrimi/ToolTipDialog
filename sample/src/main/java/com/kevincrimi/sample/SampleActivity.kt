package com.kevincrimi.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.skillshare.Skillshare.client.common.dialog.ToolTipDialog

import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    private val buttonPointUp by lazy { button_pointing_up }
    private val buttonNoArrowTop by lazy { button_no_arrow_top }
    private val buttonNowArrowBottom by lazy { button_no_arrow_bottom }
    private val buttonPointDown by lazy { button_pointing_down }
    private val fabButton by lazy { sample_fab_button }
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
            ToolTipDialog(this, this)
                .title("Dialog can point up!")
                .pointTo(location[0] + it.width / 2, location[1] + it.height)
                .content("This is pointing up to the button you just clicked")
                .subtitle("Tooltip with arrow")
                .setToolTipListener(toolTipListener)
                .show()
        }

        buttonNoArrowTop.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            ToolTipDialog(this, this)
                .title("Dialog can appear below!")
                .setYPosition(location[1] + it.height)
                .content("This is underneath the button you just clicked")
                .subtitle("Tooltip without arrow")
                .setToolTipListener(toolTipListener)
                .show()
        }

        buttonNowArrowBottom.setOnClickListener {
            ToolTipDialog(this, this)
                .title("Dialog with Default location")
                .content("When you don't set any location for the dialog it presents on top by default")
                .subtitle("Default Tooltip")
                .setToolTipListener(toolTipListener)
                .show()
        }

        buttonPointDown.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            ToolTipDialog(this, this)
                .title("Dialog can point down!")
                .pointTo(location[0] + it.width / 2, location[1] - it.height)
                .content("This is pointing up to the button you just clicked")
                .subtitle("Tooltip with arrow")
                .setToolTipListener(toolTipListener)
                .show()
        }

        fabButton.setOnClickListener {
            val location = intArrayOf(0,0)
            it.getLocationInWindow(location);
            ToolTipDialog(this, this)
                .title("You can point to individual views!")
                .pointTo(location[0] + it.width / 2, location[1] - it.height)
                .content("You can point to any of your views on screen just like this FAB")
                .subtitle("Tooltip with arrow")
                .setToolTipListener(toolTipListener)
                .show()
        }
    }
}
