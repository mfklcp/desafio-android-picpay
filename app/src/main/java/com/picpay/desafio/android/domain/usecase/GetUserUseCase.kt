package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.service.repository.GetUserRepository
import com.picpay.desafio.android.utils.PicPayResult

class GetUserUseCase(
    private val getUserRepository: GetUserRepository
) {

    suspend fun invoke(): PicPayResult<List<User>> {
        return getUserRepository.getListUsers()
    }
}
