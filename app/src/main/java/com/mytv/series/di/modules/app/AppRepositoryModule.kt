package com.mytv.series.di.modules.app

import com.mytv.top.series.di.modules.TopSeriesRepositoryModule
import dagger.Module

@Module(includes = [TopSeriesRepositoryModule::class])
class AppRepositoryModule