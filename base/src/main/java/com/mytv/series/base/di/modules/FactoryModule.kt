package com.mytv.series.base.di.modules

import androidx.lifecycle.ViewModelProvider
import com.mytv.series.base.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryModule {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}