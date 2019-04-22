package com.mytv.series.base.images

import android.widget.ImageView
import io.reactivex.Completable

interface BaseImageLoader {

    fun init(): Completable

    fun loadImageInto(
        image: String,
        imageView: ImageView,
        defaultHeight: Int,
        defaultWidth: Int,
        type: ImageConfigurationType
    )

}