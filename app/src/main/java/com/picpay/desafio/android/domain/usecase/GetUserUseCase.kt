package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.repository.GetUserRepository
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.utils.PicPayResult

class GetUserUseCase(private val getUserRepository: GetUserRepository) {

    suspend fun invoke(): PicPayResult<List<User>> {
        return try {
            val result: List<User> = getUserRepository.getListUsers()
            if (result.isNotEmpty()) {
                PicPayResult.Success(sortUsers(result))
            } else {
                PicPayResult.Error(API_FAIL)
            }
        } catch (exception: Exception) {
            PicPayResult.Error(exception.toString())
        }
    }

    private fun sortUsers(users: List<User>): List<User> {
        return users.sortedBy {
            it.username
        }
    }

    companion object {
        const val API_FAIL = "Falha na busca!"
    }
}
