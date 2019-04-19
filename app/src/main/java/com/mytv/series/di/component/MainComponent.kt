package com.mytv.series.di.component

import com.mytv.series.base.InjectableApplication
import com.mytv.series.base.di.component.BaseComponent
import com.mytv.series.base.di.modules.AppModule
import com.mytv.series.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppConfigurationModule::class,
        NetworkModule::class,
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