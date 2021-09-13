package com.dennisiluma.chatapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database


class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var firebaseUserId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentRegistrationLoginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        // Initialize Firebase Auth
        auth = Firebase.auth


        binding.fragmentRegistrationSignupButton.setOnClickListener {

            registerNewUser()

//            val email = binding.fragmentRegistrationEmailEditText.text.toString().trim()
//            val password = binding.fragmentRegistrationPasswordEditText.text.toString().trim()
//            createAccount(email, password)
        }
    }

    /*Signup new users*/
    private fun registerNewUser() {
        val userName = binding.fragmentRegistrationUserNameEditText.text.toString().trim()
        val email = binding.fragmentRegistrationEmailEditText.text.toString().trim()
        val password = binding.fragmentRegistrationPasswordEditText.text.toString().trim()
        val phoneNumber = binding.fragmentRegistrationPhoneNumberEditText.text.toString().trim()

        if (userName == "") {
            Toast.makeText(requireActivity(), "Please Enter A UserName", Toast.LENGTH_SHORT).show()
        } else if (email == "") {
            Toast.makeText(requireActivity(), "Please Enter Your Email", Toast.LENGTH_SHORT).show()
        } else if (password == "") {
            Toast.makeText(requireActivity(), "Please Enter Your Password", Toast.LENGTH_SHORT)
                .show()

        } else if (phoneNumber == "") {
            Toast.makeText(requireActivity(), "Please Enter Your PhoneNumber", Toast.LENGTH_SHORT)
                .show()

        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseUserId = auth.currentUser!!.uid
                        databaseRef = FirebaseDatabase.getInstance().reference.child("Users")
                            .child(firebaseUserId)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserId
                        userHashMap["password"] = password
                        userHashMap["email"] = email
                        userHashMap["profileimage"] =
                            "gs://chatapp-4402a.appspot.com/profileimage.png"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = userName.lowercase()
                        userHashMap["facebook"] = "https://m.facebook.com"
                        userHashMap["gender"] = "male"
                        userHashMap["state"] = "lagos"
                        userHashMap["phoneNumber"] = phoneNumber

                        databaseRef.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Succesfully save users info into DB",
                                        Toast.LENGTH_SHORT
                                    ).show()
//                                    findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//                                    requireActivity().finish()

                                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) //this will prevent back button to come to this activity_home
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Users data not logged in",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                    } else {
                        Toast.makeText(
                            requireActivity(),
                            task.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        checkIfDatabaseConnect() //check if firebase is connected
    }

    /*Check for database connectivity*/
    private fun checkIfDatabaseConnect() {
        val connectedRef = Firebase.database.getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    Toast.makeText(requireActivity(), "FireBase is Connected", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "FireBase is Not Connected, Check what's Wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(), "Listener was cancelled", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}