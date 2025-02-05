package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Project
import com.study.worknest.data.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectService private constructor() {
    companion object{
        fun fetchProjects(context: Context, callback: (MutableList<Project>?) -> Unit) {
            val apiService = APIService.getInstance(context)?.getProjectsAPI()
            apiService?.fetchProjects()?.enqueue(object : Callback<MutableList<Project>> {
                override fun onResponse(call: Call<MutableList<Project>>, response: Response<MutableList<Project>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("ProjectService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Project>>, t: Throwable) {
                    Log.e("ProjectService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })
        }
        fun getProjectById(context: Context, projectId: Int, callback: (Project?) -> Unit){
            val apiService = APIService.getInstance(context)?.getProjectsAPI()
            apiService?.getProjectById(projectId)?.enqueue(object : Callback<Project> {
                override fun onResponse(call: Call<Project>, response: Response<Project>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("ProjectService", "Error: ${response.code()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<Project>, t: Throwable) {
                    Log.e("ProjectService", "Network Error: ${t.message}")
                    callback(null)
                }
            })
        }
    }
}