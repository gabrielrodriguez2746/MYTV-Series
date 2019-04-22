package com.mytv.configuration.mappers

import com.google.gson.JsonObject
import com.mytv.base.test.readJsonFile
import com.mytv.data.models.Configuration
import io.kotlintest.shouldBe
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConfigurationMapperTest {

    lateinit var mapper: ConfigurationMapper

    @BeforeEach
    fun beforeEach() {
        mapper = spyk(ConfigurationMapper())
    }

    @Nested
    inner class `get from element` {

        @Test
        fun `empty json`() {
            val input = JsonObject()
            mapper.getFromElement(input) shouldBe getDefaultInValidConfiguration()
        }

        @Test
        fun `successful item`() {

            val input = readJsonFile(RESULT)

            mapper.getFromElement(input) shouldBe getDefaultValidConfiguration()
        }
    }

    fun getDefaultValidConfiguration(): Configuration {
        return Configuration(
            baseUrl = "https://image.tmdb.org/t/p/",
            backdropImageConfiguration = listOf("w300", "w780", "w1280", "original"),
            posterImageConfiguration = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original")
        )
    }

    fun getDefaultInValidConfiguration(): Configuration {
        return Configuration(
            baseUrl = "https://image.tmdb.org/t/p/",
            backdropImageConfiguration = listOf(),
            posterImageConfiguration = listOf()
        )
    }

    companion object {
        private const val RESULT = "configuration_object.json"
    }

}