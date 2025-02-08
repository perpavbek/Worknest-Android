package com.study.worknest.API.routes

import com.study.worknest.data.auth.LoginData
import com.study.worknest.data.auth.OTPData
import com.study.worknest.data.auth.TokenResponse
import com.study.worknest.data.requests.SignUpRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthAPI {
    @POST("auth/sign_in")
    fun logIn(@Body data: LoginData?): Call<ResponseBody>

    @POST("auth/verify-otp")
    fun verifyOtp(@Body data: OTPData): Call<TokenResponse?>

    @POST("auth/refresh")
    fun refreshToken(): Call<TokenResponse?>

    @POST("auth/sign_up")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<ResponseBody>
}