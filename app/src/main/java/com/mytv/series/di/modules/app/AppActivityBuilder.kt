package com.mytv.series.di.modules.app

import com.mytv.series.di.modules.home.HomeActivityBuilder
import com.mytv.series.di.modules.launcher.LauncherActivityBuilder
import dagger.Module

@Module(includes = [LauncherActivityBuilder::class, HomeActivityBuilder::class])
abstract class AppActivityBuilder