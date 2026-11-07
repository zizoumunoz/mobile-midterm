package com.example.midterm

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Pads the view so its content stays clear of the status and navigation bars,
 * keeping the padding already set in the layout XML on top of that.
 */
fun View.applySystemBarPadding() {
    val left = paddingLeft
    val top = paddingTop
    val right = paddingRight
    val bottom = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(left + bars.left, top + bars.top, right + bars.right, bottom + bars.bottom)
        insets
    }
}
