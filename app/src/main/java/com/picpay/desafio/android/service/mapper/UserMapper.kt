package com.picpay.desafio.android.service.mapper

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.service.model.UserResponse

class UserMapper {

    fun mapToUser(userResponse: List<UserResponse>) : List<User> =
        userResponse.map {
            User(it.id,
                it.img,
                it.name,
                it.username,)
        }.toList()
}
