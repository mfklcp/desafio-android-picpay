package com.picpay.desafio.android.service.repository

import com.picpay.desafio.android.service.api.PicPayApi
import com.picpay.desafio.android.service.model.UserResponse

class GetUserRepository(private val picPayApi: PicPayApi) {

     suspend fun getListUsers(): List<UserResponse> {
         return picPayApi.getUsers()
    }
}
