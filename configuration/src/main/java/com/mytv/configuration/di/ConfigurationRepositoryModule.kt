package com.mytv.configuration.di

import com.mytv.configuration.repository.ConfigurationRepository
import com.mytv.data.models.Configuration
import com.mytv.series.base.repositories.BaseRepository
import dagger.Binds
import dagger.Module

@Module(includes = [ConfigurationMapperModule::class, ConfigurationApiModule::class])
abstract class ConfigurationRepositoryModule {

    @Binds
    abstract fun bindConfigurationRepository(repository: ConfigurationRepository):
            @JvmSuppressWildcards BaseRepository<Any, Configuration>

}