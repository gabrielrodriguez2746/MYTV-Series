package com.mytv.series.di.modules.home

import com.mytv.home.HomeActivity
import com.mytv.series.base.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeFragmentBuilder::class])
    abstract fun bindHomeActivity(): HomeActivity
}