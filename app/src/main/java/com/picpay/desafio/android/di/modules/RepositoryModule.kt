package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.data.repository.GetUserRepository
import org.koin.dsl.module

class RepositoryModule  {
    fun provide() = listOf(repositoryModule)

    private val repositoryModule = module {
        single<GetUserRepository> { GetUserRepository(get()) }
    }
}
