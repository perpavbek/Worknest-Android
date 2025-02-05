package com.study.worknest.API.routes

import com.study.worknest.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersAPI {
    @GET("users/{userId}")
    fun getUserById(@Path("userId") userId: Int): Call<User>
}