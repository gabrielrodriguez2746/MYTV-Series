package com.mytv.home.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mytv.home.R

class TVSeriesHorizontalItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val imageViewSeries by lazy { findViewById<ImageView>(R.id.ivSeries) }
    val textViewOriginaName by lazy { findViewById<TextView>(R.id.tvOriginalName) }
    val textViewName by lazy { findViewById<TextView>(R.id.tvName) }
    val textViewPopularity by lazy { findViewById<TextView>(R.id.tvPopularity) }

    init {
        inflate(context, R.layout.item_horizontal_tv_series, this)
    }

}