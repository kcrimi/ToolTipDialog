package com.kevincrimi.tooltipdialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat

/**
 * Created by kcrimi on 2/12/18.
 * A dialog with a dialog box and an arrow to be used to point out different parts of the underlying
 * UI. PeekThroughViews can be passed in from the underlying view as well in order to draw them
 * onto a background bitmap of the the Dialog which gives the appearance of those views "peeking
 * through" the bg shade
 *
 * Example Usage:
 *
 *  ToolTipDialog toolTipDialog = new ToolTipDialog(getContext(), getActivity());
   int[] location = new int[2];
   View targetView = getContentView().findViewById(R.id.target_view_id);
   targetView.getLocationInWindow(location);
 *  toolTipDialog
 *      .pointTo(location[0] + targetView.getWidth() / 2, location[1] + targetView.getHeight())
 *      .addPeekThroughView(targetView)
 *      .addPeekThroughView(otherView)
 *      .show();
 *
 * POSSIBLE IMPROVEMENT: It would be nice to figure out a way to find the window height minus the
 *  status bar height without having to pass in an activity
 */
class ToolTipDialog(
    context: Context,
    parentActivity: Activity
) : Dialog(context, R.style.SkStyle_DialogTheme_FullScreen) {
    private val screenUtils = ScreenUtils
    private var arrowWidth = screenUtils.getPixels(context, 15f)
    private var contentView : RelativeLayout
    private var container : ViewGroup
    private var upArrow : ImageView
    private var downArrow : ImageView
    private var titleText : TextView
    private var contentText : TextView
    private var subtitleText : TextView
    private var peekThroughViews = ArrayList<View>()
    private var windowHeight: Int
    private var windowWidth: Int
    private var statusBarHeight: Int
    private var toolTipListener: ToolTipListener? = null

    private var subtitle: String = ""
    private var title: String = ""
    private var content: String = ""

    init {
        setContentView(R.layout.tootip_dialog)
        contentView = findViewById(R.id.tooltip_dialog_content_view)
        container = findViewById(R.id.container)
        upArrow = findViewById(R.id.tooltip_top_arrow)
        downArrow = findViewById(R.id.bottom_arrow)
        titleText = findViewById(R.id.title)
        contentText = findViewById(R.id.tooltip_content)
        subtitleText = findViewById(R.id.tooltip_subtitle)

        // Find the size of the application window
        val usableView = parentActivity.window.findViewById<View>(Window.ID_ANDROID_CONTENT)
        windowHeight = usableView.height
        windowWidth = usableView.width
        statusBarHeight = screenUtils.getScreenHeight(context) - windowHeight

        // Make Dialog window span the entire screen
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        container.setOnClickListener {
            toolTipListener?.onClickToolTip()
            dismiss()
        }
        contentView.setOnClickListener {
            this.dismiss() }
    }

    override fun show() {
        drawPeekingViews()
        super.show()
    }

    private fun drawPeekingViews() {
        Log.d("KEVIN", "width: $windowWidth, height: $windowHeight")
        val bitmap = Bitmap.createBitmap(windowWidth, windowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(context, R.color.tooltip_background_shade))
        for (view: View in peekThroughViews) {
            val viewBitmap = screenUtils.bitmapFromView(view)
            val xy = IntArray(2)
            view.getLocationOnScreen(xy)

            canvas.drawBitmap(viewBitmap,
                    Rect(0, 0, view.measuredWidth, view.measuredHeight),
                    Rect(xy[0], xy[1] - statusBarHeight, xy[0] + view.measuredWidth, xy[1] + view.measuredHeight -statusBarHeight),
                    null)
        }
        contentView.background = BitmapDrawable(context.resources, bitmap)
    }

    /**
     * Add views that will be drawn onto the dialog shade
     */
    fun addPeekThroughView(view : View) : ToolTipDialog {
        peekThroughViews.add(view)
        return this
    }

    /**
     * Set the position on screen for the dialog arrow to point to. This will set the dialog above
     * the point if y is below the halfway mark and below the point if the point is above halfway.
     */
    fun pointTo(x: Int, y: Int) : ToolTipDialog {
        val params = container.layoutParams as RelativeLayout.LayoutParams

        adjustContainerMargin(x)

        if (y > windowHeight / 2 - statusBarHeight) {
            // point is on the lower half of the screen, position dialog above
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            params.bottomMargin = windowHeight - y - statusBarHeight
            if (x >= 0) {
                pointArrowTo(downArrow, x)
            }
        } else {
            // point is on the upper half of the screen, position dialog below
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            params.topMargin = y - statusBarHeight
            if (x >= 0) {
                pointArrowTo(upArrow, x)
            }
        }

        container.layoutParams = params
        return this
    }

    private fun pointArrowTo(arrow: ImageView, x: Int) {
        val arrowParams = arrow.layoutParams as RelativeLayout.LayoutParams
        if (x > windowWidth / 2) {
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            arrowParams.rightMargin = windowWidth - x - arrowWidth / 2
        } else {
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            arrowParams.leftMargin = x - arrowWidth / 2
        }
        arrow.layoutParams = arrowParams
        arrow.visibility = View.VISIBLE
    }

    private fun adjustContainerMargin(x: Int) {
        var leftMargin = context.resources.getDimension(R.dimen.tooltip_dialog_activity_margin)
        var rightMargin = leftMargin
        if(x > windowWidth - windowWidth / 3) {
            leftMargin = 30f
            rightMargin = 0f
        } else if (x < windowWidth / 3) {
            leftMargin = 0f
            rightMargin = 30f
        }
        val params = container.layoutParams as RelativeLayout.LayoutParams
        params.leftMargin = screenUtils.getPixels(context, leftMargin)
        params.rightMargin = screenUtils.getPixels(context, rightMargin)
        container.layoutParams = params
    }

    /**
     * Sets the y position of the top of the dialog box. This will not use any arrow pointers
     */
    fun setYPosition(y: Int) : ToolTipDialog {
        val params = container.layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.topMargin = y - statusBarHeight
        return this
    }

    fun setToolTipListener(listener: ToolTipListener): ToolTipDialog {
        toolTipListener = listener
        return this
    }

    fun subtitle(subtitle: String): ToolTipDialog {
        subtitleText.text = subtitle
        this.subtitle = subtitle
        return this
    }

    fun title(title: String): ToolTipDialog {
        titleText.text = title
        this.title = title
        return this
    }

    fun content(content: String): ToolTipDialog {
        contentText.text = content
        this.content = content
        return this
    }

    interface ToolTipListener {
        fun onClickToolTip()
    }

    interface CtaButton {
        fun getText() : String
        fun onClick()
    }
}