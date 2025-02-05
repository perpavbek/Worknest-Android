package com.study.worknest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import com.study.worknest.data.auth.TokenResponse
import org.json.JSONObject

class TokenManager private constructor(private val context: Context) {

    private val sharedPreferencesManager = SharedPreferencesManager.getInstance(context)

    fun saveTokens(tokenResponse: TokenResponse) {
        val tokens: MutableMap<String, String> = mutableMapOf(
            "ACCESS_TOKEN" to tokenResponse.accessToken,
            "REFRESH_TOKEN" to (tokenResponse.refreshToken ?: "")
        )
        sharedPreferencesManager.saveData(tokens)
    }

    fun getAccessToken(): String? {
        return sharedPreferencesManager.getData("ACCESS_TOKEN")
    }

    fun getRefreshToken(): String? {
        return sharedPreferencesManager.getData("REFRESH_TOKEN")
    }

    fun clearTokens() {
        sharedPreferencesManager.clearData()
    }

    fun getTokenData(): JSONObject? {
        return try {
            val accessToken = getAccessToken() ?: return null
            val parts = accessToken.split(".")
            if (parts.size != 3) return null

            val payload = parts[1]
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val decodedPayload = String(decodedBytes, Charsets.UTF_8)

            JSONObject(decodedPayload)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: TokenManager? = null

        fun getInstance(context: Context): TokenManager {
            return instance ?: synchronized(this) {
                instance ?: TokenManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
