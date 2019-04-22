package com.mytv.series.di.modules.app

import com.mytv.configuration.di.ConfigurationRepositoryModule
import com.mytv.images.di.ImageLoaderModule
import dagger.Module

@Module(
    includes = [
        ConfigurationRepositoryModule::class,
        ImageLoaderModule::class
    ]
)
abstract class AppImageLoaderModule