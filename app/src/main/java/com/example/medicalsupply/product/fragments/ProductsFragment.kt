package com.example.medicalsupply.product.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.product.adapters.AdapterDevices
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.databinding.FragmentProductsBinding
import com.example.medicalsupply.product.data.MyViewModel
import com.google.firebase.auth.FirebaseAuth

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val adapterDevices: AdapterDevices by lazy { AdapterDevices() }
    private val viewModel: MyViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDevices()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsBinding.bind(view)

        binding.progress.visibility = View.VISIBLE

        viewModel.productLiveData.observe(viewLifecycleOwner, Observer {

            adapterDevices.deviceList = it
            binding.recyclerDevices.adapter = adapterDevices

            binding.progress.visibility = View.GONE

            adapterDevices.setOnclick { id ->
                findNavController()
                    .navigate(ProductsFragmentDirections.actionProductsFragmentToDetailsProductFragment(id))
            }

        })

        binding.btnLogout.setOnClickListener {
            if (auth.uid != null) {
                auth.signOut()
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}