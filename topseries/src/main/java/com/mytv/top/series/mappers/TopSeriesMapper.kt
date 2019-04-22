package com.mytv.top.series.mappers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.internal.LazilyParsedNumber
import com.mytv.data.models.TVSeries
import com.mytv.series.base.helpers.getGenericOrDefault
import com.mytv.series.base.mappers.BaseMapper
import com.mytv.top.series.helpers.getDefaultInvalidTVSeries
import io.reactivex.Single
import javax.inject.Inject

class TopSeriesMapper @Inject constructor() : @JvmSuppressWildcards BaseMapper<JsonElement, TVSeries> {

    // TODO Change for real default values
    override fun getFromElement(element: JsonElement): TVSeries {
        val tvSeriesObject = element.asJsonObject
        val id = tvSeriesObject.getGenericOrDefault("id", LazilyParsedNumber("-1"))
        return if (id.toInt() == -1) {
            getDefaultInvalidTVSeries()
        } else {
            getTVSeriesFromJsonObject(tvSeriesObject, id.toInt())
        }
    }

    internal fun getTVSeriesFromJsonObject(tvSeriesObject: JsonObject, id: Int): TVSeries {
        return with(tvSeriesObject) {
            TVSeries(
                id = id,
                originalName = getGenericOrDefault("original_name", ""),
                name = getGenericOrDefault("name", ""),
                popularity = getGenericOrDefault("popularity", LazilyParsedNumber("0.0")).toDouble(),
                overview = getGenericOrDefault("overview", ""),
                voteAverage = getGenericOrDefault("vote_average", LazilyParsedNumber("0.0")).toDouble(),
                posterPath = getGenericOrDefault("poster_path", ""),
                backdropPath = getGenericOrDefault("backdrop_path", ""),
                firstAirDate = getGenericOrDefault("first_air_date", "")
            )
        }
    }
}