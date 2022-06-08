package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.extensions.gone
import com.picpay.desafio.android.core.extensions.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.api.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainAdapter: UserListAdapter

    private val service: PicPayService by lazy {
        PicPayService.create()
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
                    val message = getString(R.string.error_api)

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
