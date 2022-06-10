package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.PicPayApi
import com.picpay.desafio.android.domain.model.User

class GetUserRepository(private val picPayApi: PicPayApi) {

     suspend fun getListUsers(): List<User> {
         return picPayApi.getUsers()
    }
}
