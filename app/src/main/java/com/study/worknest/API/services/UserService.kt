package com.study.worknest.API.services

import android.content.Context
import android.util.Log
import com.study.worknest.API.APIService
import com.study.worknest.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService private constructor() {
    companion object{
        fun getUserById(context: Context, userId: Int, callback: (User?) -> Unit) {
            val apiService = APIService.getInstance(context)?.getUsersAPI()
            apiService?.getUserById(userId)?.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("UserService", "Error: ${response.code()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("UserService", "Network Error: ${t.message}")
                    callback(null)
                }
            })
        }
    }
}