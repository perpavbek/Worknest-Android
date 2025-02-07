package com.study.worknest.API.cookie

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistentCookieJar(context: Context) : CookieJar {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("cookie_prefs", Context.MODE_PRIVATE)

    private val cookieStore: MutableMap<String, List<Cookie>> = mutableMapOf()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host()] = cookies
        saveCookiesToPrefs(url.host(), cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return loadCookiesFromPrefs(url.host())
    }

    private fun saveCookiesToPrefs(host: String, cookies: List<Cookie>) {
        val cookieStrings = cookies.map { it.toString() }
        sharedPreferences.edit().putStringSet(host, cookieStrings.toSet()).apply()
    }

    private fun loadCookiesFromPrefs(host: String): List<Cookie> {
        val cookieStrings = sharedPreferences.getStringSet(host, emptySet()) ?: emptySet()
        val cookies = mutableListOf<Cookie>()

        val httpUrl = HttpUrl.Builder().scheme("http").host(host).build()
        cookieStrings.forEach { cookieString ->
            Cookie.parse(httpUrl, cookieString)?.let { cookies.add(it) }
        }

        return cookies
    }

    fun getTokenFromCookies(): String? {
        cookieStore.values.flatten().forEach { cookie ->
            if (cookie.name() == "refresh") return cookie.value()
        }

        sharedPreferences.all.forEach { (host, value) ->
            if (value is Set<*>) {
                value.forEach { cookieString ->
                    val httpUrl = HttpUrl.Builder().scheme("http").host(host).build()
                    val cookie = Cookie.parse(httpUrl, cookieString.toString())
                    if (cookie?.name() == "refresh") {
                        return cookie.value()
                    }
                }
            }
        }
        return null
    }


    fun clearCookies() {
        sharedPreferences.edit().clear().apply()
        cookieStore.clear()
    }
}

