package com.mytv.series.base.ui.generics

import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.mytv.common.isMarshmallow

fun View.addRippleForeground() {
    if (isMarshmallow()) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        foreground = ContextCompat.getDrawable(context, outValue.resourceId)
    }
}