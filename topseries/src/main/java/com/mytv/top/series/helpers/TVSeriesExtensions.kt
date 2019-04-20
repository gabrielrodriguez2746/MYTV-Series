package com.mytv.top.series.helpers

import com.mytv.data.models.TVSeries

fun getDefaultInvalidTVSeries(): TVSeries {
    return TVSeries(
        id = -1,
        originalName = "",
        name = "",
        popularity = 0.0,
        overview = "",
        voteAverage = 0.0,
        posterPath = "",
        backdropPath = "",
        firstAirDate = ""
    )
}