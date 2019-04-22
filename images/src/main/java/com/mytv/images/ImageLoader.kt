package com.mytv.images

import android.widget.ImageView
import com.mytv.data.models.Configuration
import com.mytv.series.base.helpers.tryOrDefault
import com.mytv.series.base.helpers.tryOrPrintException
import com.mytv.series.base.images.BaseImageLoader
import com.mytv.series.base.images.ImageConfigurationType
import com.mytv.series.base.repositories.BaseRepository
import com.squareup.picasso.Picasso
import io.reactivex.Completable
import javax.inject.Inject

class ImageLoader @Inject constructor(
    private val picasso: Picasso,
    private val repository: @JvmSuppressWildcards BaseRepository<Any, Configuration>
) : BaseImageLoader {

    override fun loadImageInto(
        image: String,
        imageView: ImageView,
        defaultHeight: Int,
        defaultWidth: Int,
        type: ImageConfigurationType
    ) {
        tryOrPrintException {
            val width = imageView.measuredWidth.takeIf { it > 0 } ?: defaultWidth
            val height = imageView.measuredHeight.takeIf { it > 0 } ?: defaultHeight
            val imageUrl = with(repository.getSyncData()) {
                val imageConfiguration = if (type.isBackDrop()) {
                    backdropImageConfiguration
                } else {
                    posterImageConfiguration
                }
                getUrlFromConfiguration(baseUrl, imageConfiguration, image, width)
            }
            picasso.load(imageUrl).resize(width, height).into(imageView)
        }
    }

    override fun init(): Completable {
        return repository.getCompletableData()
    }

    private fun getUrlFromConfiguration(
        baseUrl: String, configuration: List<String>, pathEnd: String, width: Int
    ): String {
        val defaultValue = configuration.firstOrNull().orEmpty()
        val backdropConfiguration = tryOrDefault({
            configuration.map {
                Integer.parseInt(it.substring(1, it.lastIndex))
            }.indexOfFirst { it > width }
        }, defaultValue)
        return "$baseUrl$backdropConfiguration$pathEnd"
    }
}