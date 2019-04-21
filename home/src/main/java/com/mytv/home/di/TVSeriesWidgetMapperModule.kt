package com.mytv.home.di

import com.mytv.data.models.TVSeries
import com.mytv.home.mappers.TVSeriesWidgetMapper
import com.mytv.home.models.TVSeriesWidgetModel
import com.mytv.series.base.mappers.BaseMapper
import dagger.Binds
import dagger.Module

@Module
abstract class TVSeriesWidgetMapperModule {

    @Binds
    abstract fun bindWidgetTVSeriesMapper(mapper: TVSeriesWidgetMapper) : @JvmSuppressWildcards BaseMapper<TVSeries, TVSeriesWidgetModel>

}