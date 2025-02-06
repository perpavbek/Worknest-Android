package com.study.worknest.API.routes

import com.study.worknest.data.Team
import com.study.worknest.data.requests.TeamRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamsAPI {
    @GET("teams")
    fun fetchTeams(): Call<MutableList<Team>>
    @POST("teams")
    fun createTeam(@Body team: TeamRequest) : Call<ResponseBody>
    @GET("teams/project/{projectId}")
    fun getTeamsByProjectId(@Path("projectId") projectId: Int) : Call<MutableList<Team>>
}