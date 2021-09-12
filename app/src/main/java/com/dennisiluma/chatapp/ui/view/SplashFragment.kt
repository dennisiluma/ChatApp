package com.dennisiluma.chatapp.ui.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding:FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({

            if(collectSharedPreferences()){
                findNavController().navigate(R.id.action_splashFragment_to_signupLoginFragment)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_onboardingViewPagerFragment)
            }
        }, 3000)

        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }
    fun collectSharedPreferences():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE) //our shared preference
        return  sharedPref.getBoolean("Finished", true)//look for the stored value "Finished", when found return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}