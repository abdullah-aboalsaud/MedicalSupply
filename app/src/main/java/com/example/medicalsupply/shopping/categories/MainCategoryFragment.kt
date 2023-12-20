package com.example.medicalsupply.shopping.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentMainCategoryBinding
import com.example.medicalsupply.databinding.ItemSpecialRvBinding
import com.example.medicalsupply.shopping.adapters.BestDealsAdapter
import com.example.medicalsupply.shopping.adapters.BestProductAdapter
import com.example.medicalsupply.shopping.adapters.SpecialProductAdapter
import com.example.medicalsupply.shopping.viewmodels.MainCategoryViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductAdapter: SpecialProductAdapter
    private lateinit var bestProductAdapter: BestProductAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchSpecialProducts()
        viewModel.fetchBestDeals()
        viewModel.fetchBestProducts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpecialProductRv()
        setUpBestDealsRv()
        setUpBestProductsRv()


    }

    private fun setUpBestProductsRv() {
        bestProductAdapter=BestProductAdapter()

        viewModel.bestProductsLiveData.observe(viewLifecycleOwner){productList->
            bestProductAdapter.differ.submitList(productList)
            binding.rvBestProducts.adapter = bestProductAdapter

            binding.progressBestProducts.visibility=View.GONE
        }

    }

    private fun setUpBestDealsRv() {
        bestDealsAdapter=BestDealsAdapter()

        viewModel.bestDealsProductsLivedata.observe(viewLifecycleOwner){productList->
            bestDealsAdapter.differ.submitList(productList)
            binding.rvBestDealsProducts.adapter = bestDealsAdapter

            binding.progressDeals.visibility=View.GONE
        }
    }

    private fun setUpSpecialProductRv() {
        specialProductAdapter = SpecialProductAdapter()

        viewModel.specialProductsLivedata.observe(viewLifecycleOwner) {
            specialProductAdapter.differ.submitList(it)
            binding.rvSpecialProducts.adapter = specialProductAdapter

            binding.progressSpecial.visibility=View.GONE
        }


    }
}