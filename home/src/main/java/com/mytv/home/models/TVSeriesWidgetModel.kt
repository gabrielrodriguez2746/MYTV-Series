package com.mytv.home.models

import android.text.SpannableString

data class TVSeriesWidgetModel(
    val id: Int,
    val name: String,
    val rate: SpannableString,
    val originalName: SpannableString,
    val image: String,
    var isFavorite: Boolean = false
)