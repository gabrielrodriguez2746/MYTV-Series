package com.mytv.series.di.modules

import com.mytv.di.HomeActivityBuilder
import com.mytv.launcher.di.LauncherActivityBuilder
import dagger.Module

@Module(includes = [LauncherActivityBuilder::class, HomeActivityBuilder::class])
abstract class AppActivityBuilder