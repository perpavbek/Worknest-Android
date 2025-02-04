package com.study.worknest.API

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.study.worknest.API.cookie.PersistentCookieJar
import com.study.worknest.API.routes.AuthAPI
import com.study.worknest.API.routes.ProjectsAPI
import com.study.worknest.API.routes.TasksAPI
import com.study.worknest.data.auth.TokenResponse
import com.study.worknest.utils.TokenManager
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class APIService private constructor(context: Context) {

    private val BASE_URL = "http://37.151.225.71:5000/api/"
    private val mRetrofit: Retrofit
    private val cookieJar = PersistentCookieJar(context)
    init {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val tokenInterceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val accessToken = TokenManager.getInstance(context).getAccessToken()

            val requestBuilder = originalRequest.newBuilder()
            if (!accessToken.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
            }

            val modifiedRequest = requestBuilder.build()
            chain.proceed(modifiedRequest)
        }
        val tokenAuthenticator = Authenticator { _, response ->
            if (responseCount(response) >= 3) {
                return@Authenticator null
            }

            synchronized(this) {
                val newAccessToken = refreshAccessToken(context)

                return@Authenticator if (newAccessToken != null) {
                    response.request().newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                } else {
                    null
                }
            }
        }

        val localDateDeserializer = JsonDeserializer { json, _, _ ->
            try {
                val dateString = json.asString
                LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
            } catch (e: Exception) {
                null
            }
        }

        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, localDateDeserializer)
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .authenticator(tokenAuthenticator)
            .cookieJar(cookieJar)
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun getAuthAPI(): AuthAPI {
        return mRetrofit.create(AuthAPI::class.java)
    }

    fun getTasksAPI(): TasksAPI {
        return mRetrofit.create(TasksAPI::class.java)
    }

    fun getProjectsAPI(): ProjectsAPI {
        return mRetrofit.create(ProjectsAPI::class.java)
    }

    fun getCookieJar(): PersistentCookieJar {
        return cookieJar
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse()
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse()
        }
        return count
    }

    private fun refreshAccessToken(context: Context): String? {
        return try {
            val refreshResponse = getAuthAPI().refreshToken().execute()
            if (refreshResponse.isSuccessful) {
                val newAccessToken = refreshResponse.body()?.accessToken
                val newRefreshToken = getCookieJar().getTokenFromCookies()

                if (!newAccessToken.isNullOrEmpty() && !newRefreshToken.isNullOrEmpty()) {
                    Log.d("", newAccessToken)
                    TokenManager.getInstance(context).saveTokens(refreshResponse.body()!!)
                    newAccessToken
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
    companion object {
        @Volatile
        private var mInstance: APIService? = null

        fun getInstance(context: Context): APIService? {
            if (mInstance == null) {
                mInstance = APIService(context)
            }
            return mInstance
        }
    }
}
