package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.service.mapper.UserMapper
import org.koin.dsl.module

class MapperModule  {
    fun provide() = listOf(mapperModule)

    private val mapperModule = module {
        single<UserMapper> { UserMapper() }
    }
}
