package com.jerlendds.moblab

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor

object DebugTools {
    fun init(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    fun networkInterceptors(): List<Interceptor> = listOf(StethoInterceptor())
}
