package com.mytv.configuration.di

import com.google.gson.JsonElement
import com.mytv.configuration.mappers.ConfigurationMapper
import com.mytv.data.models.Configuration
import com.mytv.series.base.mappers.BaseMapper
import dagger.Binds
import dagger.Module

@Module
abstract class ConfigurationMapperModule {

    @Binds
    abstract fun bindConfigurationMapper(mapper: ConfigurationMapper):
            @JvmSuppressWildcards BaseMapper<JsonElement, Configuration>

}