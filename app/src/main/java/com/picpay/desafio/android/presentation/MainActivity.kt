package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.extensions.gone
import com.picpay.desafio.android.core.extensions.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewmodel.MainVMFactory
import com.picpay.desafio.android.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var itemUserBinding: ListItemUserBinding
    private val userListAdapter = UserListAdapter()

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainVMFactory())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        itemUserBinding = ListItemUserBinding.inflate(layoutInflater)

        initViews()
        initValues()
        observeData()
    }

    private fun initViews() {
        createRecyclerView()
    }

    private fun createRecyclerView() {
        mainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
        }
    }

    private fun initValues() {
        mainViewModel.init()
    }

    private fun observeData() {
        mainViewModel.loading.observe(this, Observer<Boolean> { loading ->
            if(loading) {
                itemUserBinding.progressBarItemUser.visible()
            }else {
                itemUserBinding.progressBarItemUser.gone()
            }
        })

        mainViewModel.contactsList.observe(this, Observer<List<User>> { users ->
            userListAdapter.users = users
        })

        mainViewModel.failure.observe(this, Observer<Throwable> {
            mainBinding.recyclerView.gone()

            val message = getString(R.string.error_api)

            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        })
    }
}
