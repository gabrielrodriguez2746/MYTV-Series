package com.mytv.top.series.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TopSeriesService {

    @GET("tv/popular")
    fun getPopularTvSeries(@Query("page") page: Int) : Single<JsonObject>

}