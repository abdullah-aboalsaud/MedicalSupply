package com.example.medicalsupply.shopping.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.medicalsupply.databinding.FragmentBillingBinding

class BillingFragment : Fragment() {
    private lateinit var binding: FragmentBillingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentBillingBinding.inflate(inflater)
        return binding.root
    }

}