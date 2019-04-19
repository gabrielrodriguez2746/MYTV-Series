package com.mytv.series.base.helpers

import okhttp3.logging.HttpLoggingInterceptor

fun HttpLoggingInterceptor.applyLoggingInterceptorLogs(enableLogs: Boolean): HttpLoggingInterceptor {
    return apply {
        level = if (enableLogs) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}