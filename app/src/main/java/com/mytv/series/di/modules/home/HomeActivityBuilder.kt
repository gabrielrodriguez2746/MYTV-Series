package com.mytv.series.di.modules.home

import com.mytv.home.HomeActivity
import com.mytv.top.series.di.modules.TopSeriesRepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityBuilder {

    @ContributesAndroidInjector(modules = [TopSeriesRepositoryModule::class])
    abstract fun bindHomeActivity(): HomeActivity
}