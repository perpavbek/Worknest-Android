package com.study.worknest.API.routes

import com.study.worknest.data.Team
import retrofit2.Call
import retrofit2.http.GET

interface TeamsAPI {
    @GET("teams")
    fun fetchTeams(): Call<MutableList<Team>>
}