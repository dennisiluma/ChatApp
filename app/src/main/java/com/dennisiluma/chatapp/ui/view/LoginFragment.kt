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
import com.dennisiluma.chatapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.fragmentLoginLogInButton.setOnClickListener{
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }
    private fun logInUser(){
        val email = binding.fragmentLoginEmailAddressEditText.text.toString().trim()
        val password = binding.fragmentLoginPasswordEditText.text.toString().trim()
        if (email.isEmpty()){
            Toast.makeText(requireActivity(),"Enter your email", Toast.LENGTH_SHORT).show()

        }else if(password.isEmpty()){
            Toast.makeText(requireActivity(),"Enter your password", Toast.LENGTH_SHORT).show()
        }else{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) //this will prevent back button to come to this activity_home
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireActivity(),"We Couldn't Sign in",Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}