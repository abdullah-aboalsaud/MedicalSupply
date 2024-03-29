package com.example.medicalsupply.shopping.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.databinding.FragmentHomeBinding
import com.example.medicalsupply.shopping.adapters.HomeViewPagerAdapter
import com.example.medicalsupply.shopping.categories.DentalFragment
import com.example.medicalsupply.shopping.categories.MainCategoryFragment
import com.example.medicalsupply.shopping.categories.MedicalFragment
import com.example.medicalsupply.shopping.categories.NursingFragment
import com.example.medicalsupply.shopping.categories.PharmacyFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            MedicalFragment(),
            DentalFragment(),
            PharmacyFragment(),
            NursingFragment()
        )
        binding.viewPagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewPagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Medical Students"
                2 -> tab.text = "Dental Students"
                3 -> tab.text = "Pharmacy Students"
                4 -> tab.text = "Nursing Students"
            }

        }.attach()


      /*  binding.searchBar.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}