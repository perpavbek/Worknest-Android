package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Project
import com.study.worknest.data.Task
import com.study.worknest.data.requests.ProjectRequest
import okhttp3.ResponseBody
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
        fun createProject(context: Context, project: ProjectRequest, callback: (Boolean?) -> Unit){
            val apiService = APIService.getInstance(context)?.getProjectsAPI()
            apiService?.createProject(project)?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        callback(true)
                    } else {
                        Log.e("ProjectService", "Error: ${response.code()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ProjectService", "Network Error: ${t.message}")
                    callback(false)
                }
            })
        }
        fun updateProject(context: Context, projectId: Int, project: ProjectRequest, callback: (Boolean?) -> Unit){
            val apiService = APIService.getInstance(context)?.getProjectsAPI()
            apiService?.updateProject(projectId, project)?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val statusCode = response.code()
                    if (statusCode == 200){
                        callback(true)
                    }
                    else{
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ProjectService", "Network Error: ${t.message}")
                    callback(false)
                }
            })
        }
    }
}