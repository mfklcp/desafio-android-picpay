package com.picpay.desafio.android.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.utils.extensions.gone
import com.picpay.desafio.android.utils.extensions.visible
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.nameItemUser.text = user.name
            itemView.usernameItemUser.text = user.username
            itemView.progressBarItemUser.visible()
            Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.pictureItemUser, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBarItemUser.gone()
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBarItemUser.gone()
                    }
                })
        }
    }
}
