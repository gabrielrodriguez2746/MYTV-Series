package com.mytv.series.base.helpers

import io.kotlintest.shouldBe
import io.mockk.verify
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AnyExtensionTest {

    @Nested
    inner class `try or default` {

        private val defaultValue = "Hello Word"

        @Test
        fun `default Value`() {
            tryOrDefault({
                throw Exception()
            }, defaultValue) shouldBe defaultValue

        }

        @Test
        fun `block value`() {
            val expectedValue = "Here I'm"
            tryOrDefault({
                expectedValue
            }, defaultValue) shouldBe expectedValue
        }

    }

}