package com.dennisiluma.chatapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentOnbaordingBBinding

class OnbaordingBFragment : Fragment() {
    private var _binding:FragmentOnbaordingBBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnbaordingBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentOnboardingBGetstartedButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingViewPagerFragment_to_signupLoginFragment)
            //save sharedPref key as finished which will be used when you try to come back
            onBoardingFinished()
        }
    }
    /*Here, "onboarding" isthe name of the shared preference and her value is "finished"*/
    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true) //saved the value Finished in our shared prefernece named "onBoarding"
        editor.apply() //save it
        //check line 32 of splashFragment where it was used
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}