package com.mytv.top.series.di.modules

import com.google.gson.JsonElement
import com.mytv.data.models.TVSeries
import com.mytv.series.base.mappers.BaseMapper
import com.mytv.top.series.mappers.TopSeriesMapper
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
abstract class TopSeriesMapperModule {

    @Binds
    @Reusable
    abstract fun bindTopSeriesMapper(mapper: TopSeriesMapper): @JvmSuppressWildcards BaseMapper<JsonElement, TVSeries>

}