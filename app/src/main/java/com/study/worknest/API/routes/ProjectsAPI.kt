package com.study.worknest.API.routes

import com.study.worknest.data.Project
import com.study.worknest.data.requests.ProjectRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectsAPI {
    @GET("projects")
    fun fetchProjects(): Call<MutableList<Project>>
    @GET("projects/{projectId}")
    fun getProjectById(@Path("projectId") projectId: Int): Call<Project>?
    @POST("projects")
    fun createProject(@Body project: ProjectRequest): Call<ResponseBody>
    @PUT("projects/{projectId}")
    fun updateProject(@Path("projectId") projectId: Int, @Body project: ProjectRequest): Call<ResponseBody>
}