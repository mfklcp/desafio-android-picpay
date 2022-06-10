package com.picpay.desafio.android.util

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.service.model.UserResponse
import com.picpay.desafio.android.utils.PicPayResult

object UserFactory {

    fun createUserResponse(): UserResponse =
        UserResponse(
            1,
            "img",
            "name",
            "username",
        )

    fun createListUserResponse(): List<UserResponse> =
        listOf(
            UserResponse(2, "img2", "name2", "username2"),
            UserResponse(1, "img", "name", "username"),
            UserResponse(3, "img3", "name3", "username3"),
        )


    fun createListUser(): List<User> =
        listOf(
            User(1, "img", "name", "username"),
            User(2, "img2", "name2", "username2"),
            User(3, "img3", "name3", "username3"),
        )

    fun createPicPayResultSuccess(): PicPayResult<List<User>> =
        PicPayResult.Success(createListUser())

    fun createPicPayResultError(): PicPayResult<List<User>> =
        PicPayResult.Error("GENERIC ERROR")

}
