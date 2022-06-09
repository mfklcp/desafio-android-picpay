package com.picpay.desafio.android.di

import com.picpay.desafio.android.di.modules.ApiModule
import com.picpay.desafio.android.di.modules.ViewModelModule

class DesafioAndroidProvider {
    fun provide() = listOf(
        ViewModelModule().provide(),
        ApiModule().provide()
    ).flatten()
}
