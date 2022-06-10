package com.picpay.desafio.android.service.api

import com.picpay.desafio.android.service.model.UserResponse
import retrofit2.http.GET

interface PicPayApi {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
