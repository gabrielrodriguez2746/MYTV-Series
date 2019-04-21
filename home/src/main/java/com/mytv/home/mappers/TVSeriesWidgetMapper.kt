package com.mytv.home.mappers

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.mytv.data.models.TVSeries
import com.mytv.home.R
import com.mytv.home.models.TVSeriesWidgetModel
import com.mytv.series.base.mappers.BaseMapper
import com.mytv.series.base.providers.ResourceProvider
import com.mytv.series.base.ui.helpers.applySpans
import javax.inject.Inject

class TVSeriesWidgetMapper @Inject constructor(resourceProvider: ResourceProvider) :
    @JvmSuppressWildcards BaseMapper<TVSeries, TVSeriesWidgetModel> {

    private val colorPrimaryDark = resourceProvider.getColor(R.color.colorPrimaryDark)

    private val voteAverageHeader = resourceProvider.getString(R.string.home_vote_average)
    private val votePopularityHeader = resourceProvider.getString(R.string.home_popularity)
    private val originalNameHeader = resourceProvider.getString(R.string.home_origiunal_name)

    private val voteAverageHeaderLength = voteAverageHeader.length
    private val votePopularityHeaderLength = votePopularityHeader.length
    private val originalNameHeaderLength = originalNameHeader.length

    override fun getFromElement(element: TVSeries): TVSeriesWidgetModel {
        return with(element) {
            TVSeriesWidgetModel(id, name, getVoteAverageSpannable(), getOriginalSpannable(), backdropPath)
        }
    }

    private fun TVSeries.getVoteAverageSpannable(): SpannableString {
        val averageLastIndex = voteAverageHeaderLength + "$voteAverage".length + 1
        return SpannableString(
            "$voteAverageHeader $voteAverage. " +
                    "$votePopularityHeader $popularity"
        ).applySpans(listOf(0, voteAverageHeaderLength), styleSpan = StyleSpan(Typeface.BOLD))
            .applySpans(
                listOf(voteAverageHeaderLength + 1, averageLastIndex),
                ForegroundColorSpan(colorPrimaryDark)
            )
            .applySpans(
                listOf(averageLastIndex, averageLastIndex + votePopularityHeaderLength),
                styleSpan = StyleSpan(Typeface.BOLD)
            )
    }

    private fun TVSeries.getOriginalSpannable(): SpannableString {
        return SpannableString(
            "$originalNameHeader $originalName."
        ).applySpans(listOf(0, originalNameHeaderLength), styleSpan = StyleSpan(Typeface.BOLD))
    }
}