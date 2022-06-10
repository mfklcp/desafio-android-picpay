package com.picpay.desafio.android.data.api

import com.picpay.desafio.android.domain.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PicPayApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    companion object {
        private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

        fun create(): PicPayApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicPayApi::class.java)
    }
}
