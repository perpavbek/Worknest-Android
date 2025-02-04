package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskService private constructor() {
    companion object{
        fun fetchTasks(context: Context?, callback: (MutableList<Task>?) -> Unit) {
            val apiService = APIService.getInstance(context!!)?.getTasksAPI()
            apiService?.fetchTasks()?.enqueue(object : Callback<MutableList<Task>> {
                override fun onResponse(call: Call<MutableList<Task>>, response: Response<MutableList<Task>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TaskService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Task>>, t: Throwable) {
                    Log.e("TaskService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })
        }
    }
}