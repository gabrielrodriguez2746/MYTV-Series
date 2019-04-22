package com.mytv.series

import com.mytv.series.base.InjectableApplication
import com.mytv.series.di.component.DaggerMainComponent

class MYTVSeriesApplication : InjectableApplication() {

    companion object {
        lateinit var instance: MYTVSeriesApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun initializeInjector() {
        DaggerMainComponent.builder().application(this)
            .build()
            .apply {
                inject(this@MYTVSeriesApplication)
                getImageLoader().init().onErrorComplete().subscribe()
            }
    }

}