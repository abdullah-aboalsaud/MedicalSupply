package com.example.medicalsupply.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentDetailsProductBinding
import com.example.medicalsupply.product.data.MyViewModel

class DetailsProductFragment : Fragment() {
    private val args: DetailsProductFragmentArgs by navArgs()

    private var _binding: FragmentDetailsProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsProductBinding.bind(view)

        val deviceCode = args.productId


        viewModel.productLiveData.observe(viewLifecycleOwner, Observer {
            for (i in it.indices) {
                if (it[i].id == deviceCode) {
                    binding.tvTitleProduct.text = it[i].name
                    binding.tvDescriptionProduct.text = it[i].description
                    Glide.with(binding.root.context)
                        .load(it[i].imageUrl)
                        .into(binding.ivImageProduct)
                    binding.tvPriceProduct.text = "EGP. ${it[i].price}"

                }
            }

        })


        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}