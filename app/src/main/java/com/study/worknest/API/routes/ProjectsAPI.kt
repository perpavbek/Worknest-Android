package com.study.worknest.API.routes

import com.study.worknest.data.Project
import retrofit2.Call
import retrofit2.http.GET

interface ProjectsAPI {
    @GET("projects")
    fun fetchProjects(): Call<MutableList<Project>>
}