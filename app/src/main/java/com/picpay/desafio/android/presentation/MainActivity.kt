package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        mainBinding.btTryAgainContacts.setOnClickListener {
            mainBinding.loadingContacts.visible()
            mainBinding.emptyImageContacts.gone()
            mainBinding.btTryAgainContacts.gone()
            mainBinding.tvLoadingFailed.gone()
            mainViewModel.init()
        }
    }

    private fun createRecyclerView() {
        mainBinding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
            userListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
            mainBinding.btTryAgainContacts.gone()
            mainBinding.tvLoadingFailed.gone()
            userListAdapter.users = users
        }

        mainViewModel.failure.observe(this) {
            mainBinding.loadingContacts.gone()
            mainBinding.recyclerViewContacts.gone()
            mainBinding.btTryAgainContacts.visible()
            mainBinding.emptyImageContacts.visible()
            mainBinding.tvLoadingFailed.visible()
        }
    }
}
