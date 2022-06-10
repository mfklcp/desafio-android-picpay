package com.picpay.desafio.android.service.repository

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.service.api.PicPayApi
import com.picpay.desafio.android.service.mapper.UserMapper
import com.picpay.desafio.android.utils.PicPayResult
import com.picpay.desafio.android.utils.extensions.sortByUserName

class GetUserRepository(
    private val picPayApi: PicPayApi,
    private val userMapper: UserMapper,
) {

    suspend fun getListUsers(): PicPayResult<List<User>> {
        return try {
            val result = picPayApi.getUsers()
            if (result.isNotEmpty()) {
                PicPayResult.Success(userMapper.mapToUser(result.sortByUserName()))
            } else {
                PicPayResult.Error(API_FAIL)
            }
        } catch (exception: Exception) {
            PicPayResult.Error(exception.toString())
        }
    }

    companion object {
        const val API_FAIL = "Falha na busca!"
    }
}
