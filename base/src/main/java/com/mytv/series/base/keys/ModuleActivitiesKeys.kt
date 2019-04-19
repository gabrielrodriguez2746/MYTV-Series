package com.mytv.series.base.keys

import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

const val HOME_ACTIVITY_KEY = "HOME_ACTIVITY_KEY"

abstract class ActivityClassIntent {

    abstract fun getClassIntent(): @JvmSuppressWildcards KClass<out AppCompatActivity>
}