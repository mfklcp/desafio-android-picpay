package com.picpay.desafio.android.di.modules

import com.picpay.desafio.android.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {

    fun provide() = listOf(viewModelModule)

    private val viewModelModule = module {
        viewModel<MainViewModel> { MainViewModel(get()) }
    }
}
