package com.study.worknest.API.routes

import com.study.worknest.data.SearchRequest
import com.study.worknest.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersAPI {
    @GET("users/{userId}")
    fun getUserById(@Path("userId") userId: Int): Call<User>
    @POST("users/search")
    fun searchUser(@Body prompt: SearchRequest): Call<MutableList<User>>
    @GET("users/username/{username}")
    fun getUserByUsername(@Path("username") username: String): Call<User>
    @GET("users/team/{teamId}")
    fun getUsersByTeamId(@Path("teamId") teamId: Int): Call<MutableList<User>>
}