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
        mainBinding.buttonLoadAgainContacts.setOnClickListener {
            mainBinding.loadingContacts.visible()
            mainBinding.emptyImageContacts.gone()
            mainBinding.buttonLoadAgainContacts.gone()
            mainViewModel.init()
        }
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
            mainBinding.recyclerViewContacts.visible()
            mainBinding.loadingContacts.gone()
            mainBinding.emptyImageContacts.gone()
            mainBinding.buttonLoadAgainContacts.gone()
            userListAdapter.users = users
        }

        mainViewModel.failure.observe(this) {
            mainBinding.loadingContacts.gone()
            mainBinding.recyclerViewContacts.gone()
            mainBinding.buttonLoadAgainContacts.visible()
            mainBinding.emptyImageContacts.visible()

            Toast
                .makeText(this@MainActivity, getString(R.string.error_api), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
