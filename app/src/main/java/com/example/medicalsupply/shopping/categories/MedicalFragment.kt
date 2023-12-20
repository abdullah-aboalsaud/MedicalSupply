package com.example.medicalsupply.shopping.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.shopping.viewmodels.CategoryViewModel
import com.example.medicalsupply.shopping.viewmodels.factory.BaseCategoryViewModelFactory
import kotlinx.coroutines.launch

/** for medical students*/
class MedicalFragment : BaseCategoryFragment() {

    private val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactory(Category.Medical)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bestProdLivedata.observe(viewLifecycleOwner) {
            bestProductAdapter.differ.submitList(it)
            binding.progressProducts.visibility=View.GONE
        }

        viewModel.offerProdLivedata.observe(viewLifecycleOwner) {
            offerAdapter.differ.submitList(it)
            binding.progressProducts.visibility=View.GONE
        }

    }
}