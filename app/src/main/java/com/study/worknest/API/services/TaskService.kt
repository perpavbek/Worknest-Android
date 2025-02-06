package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Task
import com.study.worknest.data.requests.TaskRequest
import com.study.worknest.data.requests.TeamRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

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
        fun getTasksByDate(context: Context?, date: LocalDate, callback: (MutableList<Task>) -> Unit) {
            fetchTasks(context) { fetchedTasks ->
                val filteredTasks = fetchedTasks?.filter {
                    it.deadline == date
                }?.toMutableList() ?: mutableListOf()

                callback(filteredTasks)
            }
        }
        fun getTaskDates(context: Context?, callback: (MutableList<LocalDate>) -> Unit) {
            fetchTasks(context) { fetchedTasks ->
                val dates = fetchedTasks
                    ?.mapNotNull { it.deadline }
                    ?.distinct()
                    ?.toMutableList()
                    ?: mutableListOf()
                callback(dates)
            }
        }
        fun createTask(task: TaskRequest, context: Context, callback: (Boolean) -> Unit){
            val apiService = APIService.getInstance(context)?.getTasksAPI()
            apiService?.createTask(task)?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val statusCode = response.code()
                    if (statusCode == 201){
                        callback(true)
                    }
                    else{
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback(false)
                }
            })
        }
    }
}