package com.mytv.network.tv.di.modules

import com.google.gson.Gson
import com.mytv.network.tv.R
import com.mytv.series.base.network.ConnectTimeOut
import com.mytv.series.base.network.ReadTimeOut
import com.mytv.series.base.providers.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object TVClientModule {

    @Provides
    @Reusable
    @JvmStatic
    fun provideClient(
        interceptors: @JvmSuppressWildcards Set<Interceptor>,
        readTimeOut: ReadTimeOut,
        connectTimeOut: ConnectTimeOut
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(readTimeOut.timeOut, TimeUnit.SECONDS)
            connectTimeout(connectTimeOut.timeOut, TimeUnit.SECONDS)
            interceptors.forEach { interceptor ->
                addInterceptor(interceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideMilwaukeeService(
        httpClient: OkHttpClient,
        resourceProvider: ResourceProvider,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(resourceProvider.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}