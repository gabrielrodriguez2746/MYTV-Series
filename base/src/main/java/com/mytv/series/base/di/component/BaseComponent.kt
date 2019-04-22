package com.mytv.series.base.di.component

import com.mytv.series.base.InjectableApplication
import com.mytv.series.base.images.BaseImageLoader
import dagger.android.AndroidInjector

interface BaseComponent : AndroidInjector<InjectableApplication> {

    fun getImageLoader(): BaseImageLoader
}