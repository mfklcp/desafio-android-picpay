package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityContactsBinding
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.viewmodel.ContactsViewModel
import com.picpay.desafio.android.utils.extensions.gone
import com.picpay.desafio.android.utils.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityContactsBinding
    private lateinit var itemUserBinding: ListItemUserBinding
    private val userListAdapter = UserListAdapter()
    private val mainViewModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        itemUserBinding = ListItemUserBinding.inflate(layoutInflater)

        initViews()
        initValues()
        observeData()
    }

    private fun initViews() {
        createRecyclerView()
        mainBinding.loadingContacts.visible()
    }

    private fun createRecyclerView() {
        mainBinding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
        }
    }

    private fun initValues() {
        mainViewModel.init()
    }

    private fun observeData() {
        mainViewModel.contactsList.observe(this) { users ->
            mainBinding.loadingContacts.gone()
            mainBinding.emptyImageContacts.gone()
            userListAdapter.users = users
        }

        mainViewModel.failure.observe(this) {
            mainBinding.recyclerViewContacts.gone()
            mainBinding.emptyImageContacts.visible()

            val message = getString(R.string.error_api)

            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}
