package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.PicPayApi
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.utils.PicPayResult

class GetUserRepository(private val picPayApi: PicPayApi) {

     suspend fun getListUsers(): PicPayResult<List<User>> {
        return try {
            val result: List<User> = picPayApi.getUsers()
            if (result.isNotEmpty()) {
                PicPayResult.Success(result)
            } else {
                PicPayResult.Error("Falha na busca!")
            }
        } catch (exception: Exception) {
            PicPayResult.Error(exception.toString())
        }
    }
}
