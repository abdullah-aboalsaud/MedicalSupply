package com.example.medicalsupply.shopping.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.medicalsupply.R
import com.example.medicalsupply.databinding.FragmentMainCategoryBinding
import com.example.medicalsupply.shopping.adapters.BestDealsAdapter
import com.example.medicalsupply.shopping.adapters.BestProductAdapter
import com.example.medicalsupply.shopping.adapters.SpecialProductAdapter
import com.example.medicalsupply.shopping.fragments.HomeFragmentDirections
import com.example.medicalsupply.shopping.viewmodels.MainCategoryViewModel
import com.example.medicalsupply.utils.Resource
import com.example.medicalsupply.utils.showBottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainCategoryFragment"

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
        viewModel.fetchBestDeals()
        viewModel.fetchBestProducts()
        viewModel.fetchSpecialProducts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpecialProductRv()
        setUpBestDealsRv()
        setUpBestProductsRv()

        specialProductAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }
        bestDealsAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }
        bestProductAdapter.onClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.specialProductsSF.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        specialProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsSF.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.bestProductsSF.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }

                        is Resource.Success -> {
                            bestProductAdapter.differ.submitList(it.data)
                            hideLoading()
                        }

                        is Resource.Error -> {
                            hideLoading()
                            Log.e(TAG, it.message.toString())
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }

        }

    }

    private fun setUpBestProductsRv() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProducts.adapter = bestProductAdapter
    }

    private fun setUpBestDealsRv() {
        bestDealsAdapter = BestDealsAdapter()
        binding.rvBestDealsProducts.adapter = bestDealsAdapter
    }


    private fun setUpSpecialProductRv() {
        specialProductAdapter = SpecialProductAdapter()
        binding.rvSpecialProducts.adapter = specialProductAdapter
    }

    private fun showLoading() {
        binding.progressBestProducts.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBestProducts.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}