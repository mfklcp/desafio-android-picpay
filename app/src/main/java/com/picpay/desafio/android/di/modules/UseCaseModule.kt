package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.domain.usecase.GetUserUseCase
import org.koin.dsl.module

class UseCaseModule {

    fun provide() = listOf(useCaseModule)

    private val useCaseModule = module {
        single<GetUserUseCase> { GetUserUseCase(get(), get()) }
    }
}
