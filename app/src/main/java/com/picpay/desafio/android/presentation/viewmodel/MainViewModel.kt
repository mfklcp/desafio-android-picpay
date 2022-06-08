package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val internalContactsList = MutableLiveData<List<User>>()
    private val internalFailure = MutableLiveData<Throwable>()
    private val internalLoading = MutableLiveData<Boolean>()
    private val service: PicPayService by lazy { PicPayService.create() }

    val contactsList: LiveData<List<User>> = internalContactsList
    val failure: LiveData<Throwable> = internalFailure
    val loading: LiveData<Boolean> = internalLoading

    fun init() {
        viewModelScope.launch {
            internalLoading.value = true
            getListUser()
            internalLoading.value = false
        }
    }

    private fun getListUser() {
        service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                internalFailure.value = t
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                internalContactsList.value = response.body()
            }
        })
    }
}
