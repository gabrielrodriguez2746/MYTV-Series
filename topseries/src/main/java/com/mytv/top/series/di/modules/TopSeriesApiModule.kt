package com.mytv.top.series.di.modules

import com.mytv.top.series.rest.TopSeriesService
import dagger.Binds
import dagger.Module
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject

@Module
abstract class TopSeriesApiModule {

    @Binds
    @Reusable
    abstract fun bindPopularService(service: TopSeriesSeriesImpl): TopSeriesService

}

class TopSeriesSeriesImpl @Inject constructor(retrofit: Retrofit) :
    TopSeriesService by retrofit.create(TopSeriesService::class.java)