package com.picpay.desafio.android.factory

import com.picpay.desafio.android.service.model.UserResponse

object UserFactory {

    fun createUserResponse(): UserResponse =
        UserResponse(
            1,
            "img",
            "name",
            "username",
        )

    fun createListUser(): List<UserResponse> =
        listOf(
            UserResponse(1, "img", "name", "username"),
            UserResponse(2, "img2", "name2", "username2"),
            UserResponse(3, "img3", "name3", "username3"),
        )
}
