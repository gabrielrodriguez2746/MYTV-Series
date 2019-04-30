package com.mytv.series.di.modules.launcher

import androidx.appcompat.app.AppCompatActivity
import com.mytv.series.MainActivity
import com.mytv.launcher.activities.SplashActivity
import com.mytv.series.base.intents.ActivityClassIntent
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject
import kotlin.reflect.KClass

@Module
abstract class LauncherActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindLauncherActivity(): SplashActivity

    @Binds
    abstract fun bindActivityClassIntent(activityClassIntent: HomeActivityClass): ActivityClassIntent

}

class HomeActivityClass @Inject constructor() : ActivityClassIntent() {
    override fun getClassIntent(): @JvmSuppressWildcards KClass<out AppCompatActivity> = MainActivity::class
}