package com.dennisiluma.chatapp.ui.view

import android.os.Bundle
import android.renderscript.Sampler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentFriendsBinding
import com.dennisiluma.chatapp.model.Users
import com.dennisiluma.chatapp.ui.adapter.FriendsRVAdapter
import com.dennisiluma.chatapp.ui.adapter.OnItemClickListener
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendsFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { FriendsRVAdapter(this) }
    private var usersList:List<Users>? = null
    private lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = binding.friendsFragmentRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        usersList = ArrayList()
        retrieveAllUsers()

        binding.friendsFragmentSearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchUsers(p0.toString().lowercase())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun retrieveAllUsers() {
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val refUsers =
            FirebaseDatabase.getInstance().reference.child("Users") //this is querrying all users in the table Users
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (usersList as ArrayList<Users>).clear()
                if(binding.friendsFragmentSearchView.text.toString() == ""){ // only run this when user is not searching
                    for (snapshot in snapshot.children){
                        val user:Users? = snapshot.getValue(Users::class.java)
                        if((user!!.uid) != firebaseUserID){
                            (usersList as ArrayList<Users>).add(user)
                        }
                    }
                    adapter.populateUsersList(usersList as ArrayList<Users>) //populate the adapter list
                    recyclerview.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun searchUsers(str:String) {
        val firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val queryUsers = FirebaseDatabase.getInstance().reference
            .child("Users").orderByChild("search")
            .startAt(str)
            .endAt(str + "\uf8ff")
        queryUsers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (usersList as ArrayList<Users>).clear()
                for (item in snapshot.children){
                    val user:Users? = item.getValue(Users::class.java)

                    if((user!!.uid) != firebaseUserID){
                        (usersList as ArrayList<Users>).add(user)
                    }
                }
                adapter.populateUsersList(usersList as ArrayList<Users>)
                recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //interface that handles clicking COMING FROM FriendsRVAdapter
    override fun onItemClick(userUrl: String) {
        Toast.makeText(requireActivity(), "I was Clicked", Toast.LENGTH_SHORT).show()
    }

}