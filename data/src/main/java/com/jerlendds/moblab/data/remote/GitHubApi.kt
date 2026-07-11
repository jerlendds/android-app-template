package com.jerlendds.moblab.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface GitHubApi {
    @GET("users")
    fun getUsers(): Call<List<UserDto>>
}
