package com.picpay.desafio.android.di

import com.picpay.desafio.android.di.modules.ApiModule
import com.picpay.desafio.android.di.modules.RepositoryModule
import com.picpay.desafio.android.di.modules.UseCaseModule
import com.picpay.desafio.android.di.modules.ViewModelModule

class DesafioAndroidProvider {
    fun provide() = listOf(
        ApiModule().provide(),
        RepositoryModule().provide(),
        UseCaseModule().provide(),
        ViewModelModule().provide(),
    ).flatten()
}
