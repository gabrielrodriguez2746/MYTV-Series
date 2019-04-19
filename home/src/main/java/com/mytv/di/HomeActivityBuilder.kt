package com.mytv.di

import androidx.appcompat.app.AppCompatActivity
import com.mytv.home.HomeActivity
import com.mytv.series.base.keys.ActivityClassIntent
import com.mytv.series.base.keys.HOME_ACTIVITY_KEY
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Inject
import kotlin.reflect.KClass

@Module
abstract class HomeActivityBuilder {


    @Binds
    @IntoMap
    @StringKey(HOME_ACTIVITY_KEY)
    abstract fun bindActivityClassIntent(activityClassIntent: HomeActivityClass): ActivityClassIntent
}

class HomeActivityClass @Inject constructor() : ActivityClassIntent() {
    override fun getClassIntent(): @JvmSuppressWildcards KClass<out AppCompatActivity> = HomeActivity::class
}
