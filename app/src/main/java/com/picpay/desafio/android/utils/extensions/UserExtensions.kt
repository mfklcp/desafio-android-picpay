package com.picpay.desafio.android.utils.extensions

import com.picpay.desafio.android.service.model.UserResponse

fun List<UserResponse>.sortByUserName(): List<UserResponse> {
    return this.sortedBy {
        it.username
    }
}
