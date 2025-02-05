package com.study.worknest.API.routes

import com.study.worknest.data.Project
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectsAPI {
    @GET("projects")
    fun fetchProjects(): Call<MutableList<Project>>
    @GET("projects/{projectId}")
    fun getProjectById(@Path("projectId") projectId: Int): Call<Project>
}