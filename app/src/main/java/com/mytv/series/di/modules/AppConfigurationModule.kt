package com.mytv.series.di.modules

import android.content.Context
import com.mytv.series.BuildConfig
import com.mytv.series.base.config.BaseConfiguration
import com.mytv.series.base.providers.ResourceProvider
import dagger.Binds
import dagger.Module
import javax.inject.Inject

@Module
abstract class AppConfigurationModule {

    @Binds
    abstract fun bindAppConfiguration(configuration: AppConfiguration): BaseConfiguration

    @Binds
    abstract fun bindResourceProvider(resourceProvider: AppResourceProvider): ResourceProvider
}

class AppConfiguration @Inject constructor() : BaseConfiguration {
    override fun areAppLogsEnable(): Boolean = BuildConfig.DEBUG
}

class AppResourceProvider @Inject constructor(private val context: Context) : ResourceProvider {

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg args: Any?): String = context.getString(id, args)

    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)

}