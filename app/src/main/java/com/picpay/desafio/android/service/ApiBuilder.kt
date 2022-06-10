package com.picpay.desafio.android.service

import android.content.Context
import com.picpay.desafio.android.service.api.PicPayApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

/**
 * Based implementation on https://bapspatil.medium.com/caching-with-retrofit-store-responses-offline-71439ed32fda
 * and https://stackoverflow.com/questions/62687724/how-to-check-internet-without-networkinfojava
 */

class ApiBuilder(context: Context) {

    fun create(): PicPayApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PicPayApi::class.java)

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(context.cacheDir, cacheSize)

    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork())
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            chain.proceed(request)
        }
        .build()

    private fun hasNetwork(): Boolean {
        try {
            val urlc: HttpURLConnection = URL("http://www.google.com").openConnection() as HttpURLConnection
            urlc.setRequestProperty("User-Agent", "Test")
            urlc.setRequestProperty("Connection", "close")
            urlc.connectTimeout = 100
            urlc.connect()
            return urlc.responseCode == 200
        } catch (e: Exception) {}
        return false
    }

    companion object {
        private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
    }
}
