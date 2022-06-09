package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.domain.api.PicPayService
import org.koin.dsl.module

class ApiModule {
    fun provide() = listOf(apiModuleKoin)

    private val apiModuleKoin = module {
        single<PicPayService> { PicPayService.create() }
    }
}
