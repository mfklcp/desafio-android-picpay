package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUserUseCase
import com.picpay.desafio.android.utils.result
import kotlinx.coroutines.launch

class MainViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val internalUserList = MutableLiveData<List<User>>()
    private val internalFailure = MutableLiveData<String>()

    val contactsList: LiveData<List<User>> = internalUserList
    val failure: LiveData<String> = internalFailure

    fun init() {
        viewModelScope.launch {
            getUserUseCase.invoke().result(
                onSuccess = ::onGetUserSuccess,
                onError = ::onFailure,
            )
        }
    }

    private fun onGetUserSuccess(listUser: List<User>){
        internalUserList.value = listUser
    }

    private fun onFailure(error: String){
        internalFailure.value = error
    }
}
