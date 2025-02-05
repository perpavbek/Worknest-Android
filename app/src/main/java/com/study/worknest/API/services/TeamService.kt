package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.Project
import com.study.worknest.data.Task
import com.study.worknest.data.Team
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
    }
}