package com.mytv.configuration.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    fun getConfiguration(): Single<JsonObject>

}