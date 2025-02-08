package com.study.worknest.API.services

import android.content.Context
import com.study.worknest.API.APIService
import com.study.worknest.data.auth.LoginData
import com.study.worknest.data.auth.OTPData
import com.study.worknest.data.auth.TokenResponse
import com.study.worknest.data.requests.SignUpRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService private constructor() {
    companion object{

        fun logIn(username: String, password: String, context: Context, callback: (Boolean) -> Unit){
            val loginData = LoginData(username, password)
            val apiService = APIService.getInstance(context)?.getAuthAPI()
            apiService?.logIn(loginData)?.enqueue(object : Callback<ResponseBody> {
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
                    callback(false)
                }
            })
        }

        fun verifyOTP(otpData: OTPData, context: Context, callback: (TokenResponse?) -> Unit){
            val apiService = APIService.getInstance(context)?.getAuthAPI()
            apiService?.verifyOtp(otpData)?.enqueue(object : Callback<TokenResponse?> {
                override fun onResponse(call: Call<TokenResponse?>, response: Response<TokenResponse?>) {
                    var tokenResponse = response.body()
                    tokenResponse?.refreshToken = APIService.getInstance(context)?.getCookieJar()?.getTokenFromCookies()
                    callback(tokenResponse)
                }

                override fun onFailure(call: Call<TokenResponse?>, t: Throwable) {
                    callback(null)
                }
            })
        }

        fun signUp(signUpRequest: SignUpRequest, context: Context, callback: (Boolean) -> Unit){
            val apiService = APIService.getInstance(context)?.getAuthAPI()
            apiService?.signUp(signUpRequest)?.enqueue(object : Callback<ResponseBody> {
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