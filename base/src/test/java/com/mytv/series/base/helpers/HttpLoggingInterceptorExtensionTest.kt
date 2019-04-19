package com.mytv.series.base.helpers

import io.kotlintest.shouldBe
import io.mockk.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.junit.jupiter.api.Test

class HttpLoggingInterceptorExtensionTest {

    private val interceptor: HttpLoggingInterceptor = spyk()

    @Test
    fun `logs enabled`() {
        interceptor.applyLoggingInterceptorLogs(true)
        interceptor.level shouldBe BODY
    }

    @Test
    fun `logs disabled`() {
        interceptor.applyLoggingInterceptorLogs(false)
        interceptor.level shouldBe NONE
    }

}