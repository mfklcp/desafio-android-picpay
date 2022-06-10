package com.picpay.desafio.android.utils

sealed class PicPayResult<out T> {
    data class Success<out T>(val data: T) : PicPayResult<T>()
    data class Error(val exception: String) : PicPayResult<Nothing>()
}

fun <T : Any> PicPayResult<T>.result(
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit
){
    return when (this) {
        is PicPayResult.Success -> onSuccess(this.data)
        is PicPayResult.Error -> onError(this.exception)
    }
}
