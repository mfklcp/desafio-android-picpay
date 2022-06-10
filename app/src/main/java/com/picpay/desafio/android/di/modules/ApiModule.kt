package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.service.api.PicPayApi
import org.koin.dsl.module

class ApiModule {
    fun provide() = listOf(apiModule)

    private val apiModule = module {
        single<PicPayApi> { PicPayApi.create() }
    }
}
