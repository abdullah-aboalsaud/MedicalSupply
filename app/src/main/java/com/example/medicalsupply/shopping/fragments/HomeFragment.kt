package com.example.medicalsupply.shopping.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.shopping.adapters.AdapterDevices
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.data.MyViewModel
import com.example.medicalsupply.databinding.FragmentHomeBinding
import com.example.medicalsupply.models.ModelProduct
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var productList: ArrayList<ModelProduct>? = null

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.progress.visibility = View.VISIBLE

        viewModel.productLiveData.observe(viewLifecycleOwner, Observer {

            productList = it
            adapterDevices.deviceList = it
            binding.recyclerDevices.adapter = adapterDevices

            binding.progress.visibility = View.GONE

            adapterDevices.setOnclick { id ->
                findNavController()
                    .navigate(
                        HomeFragmentDirections.actionProductsFragmentToDetailsProductFragment(
                            id
                        )
                    )
            }
            binding.ibCart.setOnClickListener {
                findNavController()
                    .navigate(HomeFragmentDirections.actionProductsFragmentToCartFragment())
            }
        })

        binding.btnLogout.setOnClickListener {
            if (auth.uid != null) {
                auth.signOut()
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }


        binding.search.doAfterTextChanged {
            var query = it.toString()
            val newList = productList?.filter {
                it.name.toLowerCase().contains(query.toLowerCase())
            }

            if (!newList.isNullOrEmpty())
                adapterDevices.updateData(newList as ArrayList<ModelProduct>)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}