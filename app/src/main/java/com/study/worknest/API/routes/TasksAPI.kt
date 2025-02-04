package com.study.worknest.API.routes

import com.study.worknest.data.Task
import com.study.worknest.data.auth.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface TasksAPI {
    @GET("tasks")
    fun fetchTasks(): Call<MutableList<Task>>
}