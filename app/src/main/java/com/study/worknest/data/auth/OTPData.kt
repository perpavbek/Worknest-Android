package com.study.worknest.data.auth

import com.google.gson.annotations.SerializedName

data class OTPData(
    @SerializedName("username")
    val username: String,

    @SerializedName("otp")
    val otp: String
)