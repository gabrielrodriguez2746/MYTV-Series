package com.mytv.top.series.di.modules

import com.mytv.data.models.TVSeries
import com.mytv.series.base.di.ActivityScope
import com.mytv.series.base.repositories.BaseRepository
import com.mytv.top.series.repository.TopSeriesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [TopSeriesApiModule::class, TopSeriesMapperModule::class])
abstract class TopSeriesRepositoryModule {

    @Binds
    abstract fun bindTopSeriesRepository(repository: TopSeriesRepository): @JvmSuppressWildcards BaseRepository<Int, TVSeries>

}