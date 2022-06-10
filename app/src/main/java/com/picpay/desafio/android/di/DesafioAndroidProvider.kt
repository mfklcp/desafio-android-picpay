package com.picpay.desafio.android.di

import com.picpay.desafio.android.di.modules.ApiModule
import com.picpay.desafio.android.di.modules.RepositoryModule
import com.picpay.desafio.android.di.modules.ViewModelModule

class DesafioAndroidProvider {
    fun provide() = listOf(
        ApiModule().provide(),
        RepositoryModule().provide(),
        ViewModelModule().provide(),
    ).flatten()
}
