package com.mytv.series.base.images

inline class ImageConfigurationType(val type: Int) {

    fun isBackDrop() = type == 0
}