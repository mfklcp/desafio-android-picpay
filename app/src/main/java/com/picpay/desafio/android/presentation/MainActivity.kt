package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewmodel.MainViewModel
import com.picpay.desafio.android.utils.extensions.gone
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var itemUserBinding: ListItemUserBinding
    private val userListAdapter = UserListAdapter()
    private val mainViewModel: MainViewModel by viewModel()

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
        mainViewModel.contactsList.observe(this) { users ->
            userListAdapter.users = users
        }

        mainViewModel.failure.observe(this) {
            mainBinding.recyclerView.gone()

            val message = getString(R.string.error_api)

            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}
