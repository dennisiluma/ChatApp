package com.dennisiluma.chatapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.dennisiluma.chatapp.R
import com.dennisiluma.chatapp.databinding.FragmentOnboardingABinding

class OnboardingAFragment : Fragment() {
    private var _binding: FragmentOnboardingABinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewpager: ViewPager2? = activity?.findViewById(R.id.viewPager2)
        binding.fragmentOnboardingANextButton.setOnClickListener {
            viewpager?.currentItem = 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}