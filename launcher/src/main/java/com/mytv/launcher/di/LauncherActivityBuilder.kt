package com.mytv.launcher.di

import com.mytv.launcher.activities.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LauncherActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindLauncherActivity(): SplashActivity

}