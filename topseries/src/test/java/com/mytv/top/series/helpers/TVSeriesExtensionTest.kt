package com.mytv.top.series.helpers

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TVSeriesExtensionTest {

    @Nested
    inner class `get default invalid TVSeries` {

        @Test
        fun `get`() {
            with(getDefaultInvalidTVSeries()) {
                id shouldBe -1
                originalName shouldBe ""
                name shouldBe ""
                popularity shouldBe 0.0
                overview shouldBe ""
                voteAverage shouldBe 0.0
                posterPath shouldBe ""
                backdropPath shouldBe ""
                firstAirDate shouldBe ""
            }
        }
    }

}