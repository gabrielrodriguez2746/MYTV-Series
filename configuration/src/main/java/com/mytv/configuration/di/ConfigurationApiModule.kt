package com.mytv.configuration.di

import com.mytv.configuration.rest.ConfigurationService
import dagger.Binds
import dagger.Module
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Inject

@Module
abstract class ConfigurationApiModule {

    @Binds
    @Reusable
    abstract fun bindConfigurationService(service: ConfigurationServiceImpl): ConfigurationService

}

class ConfigurationServiceImpl @Inject constructor(retrofit: Retrofit) :
    ConfigurationService by retrofit.create(ConfigurationService::class.java)