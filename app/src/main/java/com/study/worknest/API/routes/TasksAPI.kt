package com.study.worknest.API.routes

import com.study.worknest.data.Task
import com.study.worknest.data.auth.LoginData
import com.study.worknest.data.requests.TaskRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TasksAPI {
    @GET("tasks")
    fun fetchTasks(): Call<MutableList<Task>>
    @POST("tasks")
    fun createTask(@Body task: TaskRequest): Call<ResponseBody>
}