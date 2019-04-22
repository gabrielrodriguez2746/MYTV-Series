package com.mytv.series.di.modules.home

import com.mytv.home.di.TVSeriesWidgetMapperModule
import com.mytv.home.fragments.TVSeriesFragment
import com.mytv.home.viewModels.TVSeriesViewModelModule
import com.mytv.series.base.di.ActivityScope
import com.mytv.top.series.di.modules.TopSeriesRepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuilder {

    @ContributesAndroidInjector(
        modules = [
            TopSeriesRepositoryModule::class,
            TVSeriesViewModelModule::class,
            TVSeriesWidgetMapperModule::class
        ]
    )
    @ActivityScope
    abstract fun bindTVSeriesFragment(): TVSeriesFragment

}