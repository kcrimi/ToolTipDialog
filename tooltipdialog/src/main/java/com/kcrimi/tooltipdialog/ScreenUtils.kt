package com.kcrimi.tooltipdialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics


/**
 * Copyright (c) $today.year.
 * Created by Kevin Crimi as part of the ToolTipDialog library published for free usage as determined by the Apache 2.0 license.
 * https://github.com/kcrimi/ToolTipDialog
**/
internal object ScreenUtils {

    fun getPixels(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun getScreenHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            windowMetrics.bounds.height()
        } else {
            val size = Point()
            val display = wm.defaultDisplay
            display.getSize(size)
            size.y
        }
    }

    fun getCutoutHeight(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val inset = wm.currentWindowMetrics.windowInsets.getInsets(WindowInsets.Type.tappableElement())
            inset.top
        } else {
            0
        }

    }

    fun bitmapFromView(view: View): Bitmap {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }
}