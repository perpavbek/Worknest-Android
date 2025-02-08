package com.study.worknest.API.routes

import com.study.worknest.data.Task
import com.study.worknest.data.auth.LoginData
import com.study.worknest.data.requests.TaskRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TasksAPI {
    @GET("tasks")
    fun fetchTasks(): Call<MutableList<Task>>
    @POST("tasks")
    fun createTask(@Body task: TaskRequest): Call<ResponseBody>
    @PUT("tasks/{taskId}")
    fun updateTaskById(@Body task: Task, @Path("taskId") taskId: Int): Call<ResponseBody>
}