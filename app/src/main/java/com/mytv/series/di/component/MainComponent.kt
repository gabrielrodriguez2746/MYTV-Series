package com.mytv.series.di.component

import com.mytv.series.base.InjectableApplication
import com.mytv.series.base.di.component.BaseComponent
import com.mytv.series.base.di.modules.AppModule
import com.mytv.series.base.di.modules.FactoryModule
import com.mytv.series.di.modules.app.AppActivityBuilder
import com.mytv.series.di.modules.app.AppConfigurationModule
import com.mytv.series.di.modules.app.AppImageLoaderModule
import com.mytv.series.di.modules.app.AppNetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FactoryModule::class,
        AppImageLoaderModule::class,
        AppConfigurationModule::class,
        AppActivityBuilder::class,
        AppNetworkModule::class,
        AppModule::class
    ]
)
interface MainComponent : BaseComponent {

    @Component.Builder
    interface Builder {

        fun build(): BaseComponent

        @BindsInstance
        fun application(application: InjectableApplication): Builder
    }

}