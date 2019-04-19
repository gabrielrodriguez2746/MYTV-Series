package com.mytv.series.base.di.modules

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mytv.series.base.InjectableApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesContext(app: InjectableApplication): Context = app.applicationContext

    @Provides
    @Singleton
    @JvmStatic
    fun providesApplication(app: InjectableApplication): Application = app

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson() : Gson = GsonBuilder().create()

}