package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Project
import com.study.worknest.data.Task
import com.study.worknest.data.Team
import com.study.worknest.data.auth.LoginData
import com.study.worknest.data.requests.TeamRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamService private constructor() {
    companion object{
        fun fetchTeams(context: Context, callback: (MutableList<Team>?) -> Unit) {
            val apiService = APIService.getInstance(context)?.getTeamsAPI()
            apiService?.fetchTeams()?.enqueue(object : Callback<MutableList<Team>> {
                override fun onResponse(call: Call<MutableList<Team>>, response: Response<MutableList<Team>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TeamService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Team>>, t: Throwable) {
                    Log.e("TeamService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })
        }
        fun createTeam(team: TeamRequest, context: Context, callback: (Boolean) -> Unit){
            val apiService = APIService.getInstance(context)?.getTeamsAPI()
            apiService?.createTeam(team)?.enqueue(object : Callback<ResponseBody> {
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
        fun getTeamsByProjectId(context: Context, projectId: Int, callback: (MutableList<Team>?) -> Unit) {
            val apiService = APIService.getInstance(context)?.getTeamsAPI()
            apiService?.getTeamsByProjectId(projectId)?.enqueue(object : Callback<MutableList<Team>> {
                override fun onResponse(call: Call<MutableList<Team>>, response: Response<MutableList<Team>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("TeamService", "Error: ${response.code()}")
                        callback(mutableListOf())
                    }
                }

                override fun onFailure(call: Call<MutableList<Team>>, t: Throwable) {
                    Log.e("TeamService", "Network Error: ${t.message}")
                    callback(mutableListOf())
                }
            })
        }
    }
}