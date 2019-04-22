package com.mytv.images.di

import com.mytv.images.ImageLoader
import com.mytv.series.base.images.BaseImageLoader
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ImageLoaderModule {

    @Binds
    @Singleton
    abstract fun bindImageLoader(imageLoader: ImageLoader): BaseImageLoader

    @Module
    companion object {

        // TODO Add any other special configuration for now the default is ok
        @Provides
        @JvmStatic
        @Singleton
        fun getPicassoInstance() : Picasso {
            return Picasso.get()
        }

    }

}