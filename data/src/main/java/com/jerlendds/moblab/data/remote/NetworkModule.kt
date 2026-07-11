package com.jerlendds.moblab.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    fun createOkHttpClient(
        debugInterceptors: List<Interceptor> = emptyList(),
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .apply { debugInterceptors.forEach(::addNetworkInterceptor) }
            .build()
    }

    fun createGitHubApi(okHttpClient: OkHttpClient): GitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GitHubApi::class.java)
}
