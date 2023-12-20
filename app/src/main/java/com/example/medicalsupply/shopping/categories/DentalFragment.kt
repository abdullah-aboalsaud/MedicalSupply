package com.example.medicalsupply.shopping.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.shopping.viewmodels.CategoryViewModel
import com.example.medicalsupply.shopping.viewmodels.factory.BaseCategoryViewModelFactory

/** for dental students */
class DentalFragment: BaseCategoryFragment() {

    private val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactory(Category.Dental)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bestProdLivedata.observe(viewLifecycleOwner) {
            bestProductAdapter.differ.submitList(it)
        }

        viewModel.offerProdLivedata.observe(viewLifecycleOwner) {
            offerAdapter.differ.submitList(it)
        }

    }


}