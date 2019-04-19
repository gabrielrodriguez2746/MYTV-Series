package com.mytv.series.base.di.modules

import android.util.Log
import com.mytv.series.base.config.BaseConfiguration
import com.mytv.series.base.helpers.applyLoggingInterceptorLogs
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import javax.inject.Inject

@Module
abstract class BaseInterceptorModule {

    @Binds
    @Reusable
    @IntoSet
    abstract fun bindServerLogsInterceptor(interceptor: ServerLogsInterceptor): Interceptor

    @Binds
    @Reusable
    @IntoSet
    abstract fun bindHeadersInterceptor(interceptor: HeadersInterceptor): Interceptor

}

class ServerLogsInterceptor @Inject constructor(baseConfiguration: BaseConfiguration) :
    Interceptor by HttpLoggingInterceptor(Logger { message -> Log.e("SERVER", message) }).applyLoggingInterceptorLogs(
        baseConfiguration.areAppLogsEnable()
    )

class HeadersInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder
            .header(
                "Accept",
                "application/json"
            )
            .header(
                "Content-Type",
                "application/json"
            )
            .method(chain.request().method(), chain.request().body())
        return chain.proceed(requestBuilder.build())
    }
}