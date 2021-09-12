package com.dennisiluma.chatapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dennisiluma.chatapp.databinding.FragmentOnboardingVPBinding

/*THIS FRAGMENT XML LAYOUT CONTAIN A VIEWPAGER2 THAT HOLDS THE TWO SCREEN ONBOARDING FRAGMENT AS SEEN IN TH NAV GRAPH*/
class OnboardingVPFragment : Fragment() {

    private var _binding: FragmentOnboardingVPBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingVPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            OnboardingAFragment(),
            OnbaordingBFragment()
        )
        val adapter = OnboardingVPAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        // the created adapter instance above to the viewpager adapter
        binding.viewPager2.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}