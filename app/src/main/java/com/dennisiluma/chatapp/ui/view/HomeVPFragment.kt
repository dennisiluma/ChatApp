package com.dennisiluma.chatapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentHomeVPBinding
import com.dennisiluma.chatapp.model.Users
import com.dennisiluma.chatapp.ui.adapter.HomeVPAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso

class HomeVPFragment : Fragment() {
    private var _binding: FragmentHomeVPBinding? = null
    private val binding get() = _binding!!

    private var firebaseUser: FirebaseUser? = null
    private var databaseReferenceUser: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //notifies the Homeactivity(i.e activity that hosts this fragment) the this idiot fragment has options menu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeVPBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.homeToolbar

        firebaseUser = FirebaseAuth.getInstance().currentUser
        databaseReferenceUser =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        /*Displays username and profile picture on ro thr users */
        databaseReferenceUser!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Users::class.java)
//                    binding.bottomNavigationview.findViewById<BottomNavigationView>(R.id.action_home).setText(user!!.username)

                    toolbar.title =  user!!.username
//                    Picasso.get().load(user!!.profileimage).placeholder(R.drawavle.profileimage).into(binding.bottomNavigationview) //the placeholder is icon inside the drawable, it displays it untill the picasso is able to load image from the firebase
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
        /*Toolbar configurations start here*/
        // inflate our toolbar with this menu
//        val toolbar = binding.homeToolbar

//        toolbar.title = "ChatApp" //set the title, can choose to do it in xml sha
        toolbar.inflateMenu(R.menu.toolbar_menu)
//        binding.bottomNavigationview.inflateMenu(R.menu.bottom_navigation_items)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    // Navigate to settings screen
                    true
                }
                R.id.action_logout -> {
                    // Logout fragment
                    FirebaseAuth.getInstance().signOut() //signout from google
                    val intent = Intent(requireActivity(), StartActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    true
                }
                R.id.action_more -> {
                    // Save profile changes
                    true
                }
                else -> false
            }
        }
        /*Toolbar configurations ends here*/

        val tabLayout = binding.homeTabLayout
        val viewPager2 = binding.homeViewPager2
        val fragmentList = arrayListOf<Fragment>(
            ChatFragment(),
            GroupFragment(),
            FriendsFragment()
        )
        val adapter = HomeVPAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        // the created adapter instance above to the viewpager adapter
        viewPager2.adapter = adapter

        /*Set the tabLayout with swipable viewpager*/
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Chats"
                }
                1 -> {
                    tab.text = "Groups"
                }
                2 -> {
                    tab.text = "Users"
                }
                else -> tab.text = "Chats"
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


//
//    /* These are inbuild callbacks for controlling and modifying your appbar*/
//    /*this helps to merge your munu into Already action bar menu and inflates it*/
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu)
//    }
//    /*Handles click*/
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                //*If the selected item is yours, handle the touch appropriately and return true to indicate that the click event has been handled*/
//                // navigate to settings screen
//                true //meaning the even
//            }
//            R.id.action_done -> {
//                // save profile changes
//                true
//            }
//            else -> super.onOptionsItemSelected(item) //If the selected item is not yours, call the super implementation
//        }
//    }

}