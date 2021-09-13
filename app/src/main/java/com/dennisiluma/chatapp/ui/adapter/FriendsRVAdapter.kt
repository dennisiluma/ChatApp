package com.dennisiluma.chatapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FriendsRecyclerItemBinding
import com.dennisiluma.chatapp.model.Users
import com.squareup.picasso.Picasso


class FriendsRVAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<FriendsRVAdapter.ViewHolder>() {
    private var listOfUsers = ArrayList<Users>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsRVAdapter.ViewHolder {
        if (context == null) {
            context = parent.context
        }
        return ViewHolder(
            FriendsRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsRVAdapter.ViewHolder, position: Int) {
        val users: Users = listOfUsers[position]
        holder.username.text = users.username
        Picasso.get().load(users.profileimage).placeholder(R.drawable.account)
            .into(holder.profilePic)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    inner class ViewHolder(val binding: FriendsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val username = binding.username
        val profilePic = binding.profilePic
        val lastMessage = binding.messageLast
    }

    fun populateUsersList(list: ArrayList<Users>) {
        this.listOfUsers = list
        notifyDataSetChanged()
    }
}

interface OnItemClickListener {
    fun onItemClick(userUrl: String)
}