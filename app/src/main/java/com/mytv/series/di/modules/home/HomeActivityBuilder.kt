package com.mytv.series.di.modules.home

import com.mytv.series.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentBuilder::class])
    abstract fun bindHomeActivity(): MainActivity

}