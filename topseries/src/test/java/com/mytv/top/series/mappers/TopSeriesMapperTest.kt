package com.mytv.top.series.mappers

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.internal.LazilyParsedNumber
import com.mytv.base.test.readJsonFile
import com.mytv.data.models.TVSeries
import com.mytv.top.series.helpers.getDefaultInvalidTVSeries
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TopSeriesMapperTest {

    lateinit var mapper: TopSeriesMapper

    @BeforeEach
    fun beforeEach() {
        mapper = spyk(TopSeriesMapper())
    }

    @Nested
    inner class `get from element` {

        @Test
        fun `empty json`() {
            val input = JsonObject()
            mapper.getFromElement(input) shouldBe getDefaultInvalidTVSeries()
        }

        @Test
        fun `json with wrong id`() {
            val input = JsonObject().apply { add("id", JsonPrimitive("abc")) }
            mapper.getFromElement(input) shouldBe getDefaultInvalidTVSeries()
        }

        @Test
        fun `successful item`() {

            val input = JsonObject().apply { add("id", JsonPrimitive(LazilyParsedNumber("1399"))) }
            val expectedValue = getDefaultValidTVSeries()

            every { mapper.getTVSeriesFromJsonObject(eq(input), eq(1399)) } returns expectedValue

            mapper.getFromElement(input) shouldBe expectedValue
        }
    }

    @Test
    fun `get tv series from json object`() {
        mapper.getTVSeriesFromJsonObject(readJsonFile(RESULT), 1399) shouldBe getDefaultValidTVSeries()
    }


    fun getDefaultValidTVSeries(): TVSeries {
        return TVSeries(
            id = 1399,
            originalName = "Game of Thrones",
            name = "Game of Thrones",
            popularity = 672.748,
            overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
            voteAverage = 8.2,
            posterPath = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
            backdropPath = "/qsD5OHqW7DSnaQ2afwz8Ptht1Xb.jpg",
            firstAirDate = "2011-04-17"
        )
    }

    companion object {
        private const val RESULT = "tv_series_object.json"
    }

}