package com.mytv.series.di.modules.app

import com.mytv.network.tv.di.modules.TVClientModule
import com.mytv.network.tv.di.modules.TVInterceptorsModule
import com.mytv.series.base.di.ConnectTimeOut
import com.mytv.series.base.di.ReadTimeOut
import com.mytv.series.base.di.modules.BaseInterceptorModule
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(
    includes = [
        BaseInterceptorModule::class,
        TVInterceptorsModule::class,
        TVClientModule::class
    ]
)
object AppNetworkModule {

    @Reusable
    @Provides
    @JvmStatic
    @ConnectTimeOut
    fun provideConnectTimeOut(): Long = 5

    @Reusable
    @Provides
    @JvmStatic
    @ReadTimeOut
    fun provideReadTimeOut(): Long = 5

}