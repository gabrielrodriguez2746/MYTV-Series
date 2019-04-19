package com.mytv.network.tv.di.modules

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.mytv.network.tv.R
import com.mytv.series.base.helpers.getNewRequest
import com.mytv.series.base.providers.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

@Module
abstract class TVInterceptorsModule {

    @Binds
    @Reusable
    @IntoSet
    abstract fun bindLaguageInterceptor(interceptor: LanguageInterceptor): Interceptor

    @Binds
    @Reusable
    @IntoSet
    abstract fun bindApiKeyInterceptor(interceptor: ApiKeyInterceptor): Interceptor

}

class LanguageInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        val language = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
            .language.toLowerCase()
        chain.proceed(chain.request())
        return chain.proceed(chain.request().getNewRequest("language", language))
    }
}

class ApiKeyInterceptor @Inject constructor(private val provider: ResourceProvider) : Interceptor {
    override fun intercept(chain: Chain): Response {
        return chain.proceed(chain.request().getNewRequest("api_key", provider.getString(R.string.tv_api_key)))
    }
}