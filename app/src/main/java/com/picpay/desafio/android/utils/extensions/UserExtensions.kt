package com.picpay.desafio.android.utils.extensions

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.service.model.UserResponse
import com.picpay.desafio.android.utils.PicPayResult

fun List<UserResponse>.sortByUserName(): List<UserResponse> {
    return this.sortedBy {
        it.username
    }
}

fun PicPayResult<List<User>>.get(): List<User>? {
    return if (this is PicPayResult.Success) {
        this.data
    }else{
        null
    }
}
