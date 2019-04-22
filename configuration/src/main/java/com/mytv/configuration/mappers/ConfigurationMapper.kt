package com.mytv.configuration.mappers

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.mytv.data.models.Configuration
import com.mytv.series.base.helpers.getGenericOrDefault
import com.mytv.series.base.mappers.BaseMapper
import javax.inject.Inject

class ConfigurationMapper @Inject constructor() : BaseMapper<JsonElement, Configuration> {

    override fun getFromElement(element: JsonElement): Configuration {
        val imageConfigurationObject = element.asJsonObject
        val baseUrl = imageConfigurationObject.getGenericOrDefault(
            "secure_base_url",
            "https://image.tmdb.org/t/p/"
        )
        val backdropImageConfiguration = imageConfigurationObject
            .getGenericOrDefault("backdrop_sizes", JsonArray())
            .map { it.asString }

        val posterImageConfiguration = imageConfigurationObject
            .getGenericOrDefault("poster_sizes", JsonArray())
            .map { it.asString }

        return Configuration(baseUrl, backdropImageConfiguration, posterImageConfiguration)
    }
}