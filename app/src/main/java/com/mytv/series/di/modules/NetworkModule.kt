package com.mytv.series.di.modules

import com.mytv.network.tv.di.modules.TVClientModule
import com.mytv.network.tv.di.modules.TVInterceptorsModule
import com.mytv.series.base.di.modules.BaseInterceptorModule
import com.mytv.series.base.network.ConnectTimeOut
import com.mytv.series.base.network.ReadTimeOut
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
object NetworkModule {

    @Reusable
    @Provides
    @JvmStatic
    fun provideConnectTimeOut(): ConnectTimeOut {
        return ConnectTimeOut(5)
    }

    @Reusable
    @Provides
    @JvmStatic
    fun provideReadTimeOut(): ReadTimeOut {
        return ReadTimeOut(5)
    }

}