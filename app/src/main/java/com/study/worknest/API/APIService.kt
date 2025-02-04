package com.study.worknest.API

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.study.worknest.API.cookie.PersistentCookieJar
import com.study.worknest.API.routes.AuthAPI
import com.study.worknest.API.routes.TasksAPI
import com.study.worknest.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
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

    fun getCookieJar(): PersistentCookieJar {
        return cookieJar
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
