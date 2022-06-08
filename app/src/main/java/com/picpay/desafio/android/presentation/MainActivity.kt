package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.extensions.gone
import com.picpay.desafio.android.core.extensions.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainAdapter: UserListAdapter

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainAdapter = UserListAdapter()

        mainBinding.recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mainBinding.userListProgressBar.visible()

        service.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = getString(R.string.error)

                    mainBinding.userListProgressBar.gone()
                    mainBinding.recyclerView.gone()

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    mainBinding.userListProgressBar.gone()
                    mainAdapter.users = response.body()!!
                }
            })
    }
}
