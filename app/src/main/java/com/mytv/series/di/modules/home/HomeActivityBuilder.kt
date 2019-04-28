package com.mytv.series.di.modules.home

import com.mytv.home.HomeActivity
import com.mytv.series.R
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap

@Module
abstract class HomeActivityBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentBuilder::class])
    abstract fun bindHomeActivity(): HomeActivity

    @Module
    companion object {

        @Provides
        @JvmStatic
        @IntoMap
        @IntKey(0)
        fun provideHomeNavigation(): Int = R.navigation.home

        @Provides
        @JvmStatic
        @IntoMap
        @IntKey(1)
        fun provideFavoritesNavigation(): Int = R.navigation.favorites

    }
}