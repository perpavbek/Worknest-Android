package com.study.worknest.data.auth

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("auth")
    val accessToken: String,
    @SerializedName("refresh")
    var refreshToken: String?
)