package com.jerlendds.moblab

import android.content.Context
import okhttp3.Interceptor

object DebugTools {
    fun init(context: Context) = Unit
    fun networkInterceptors(): List<Interceptor> = emptyList()
}
