package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.core.KeyProducts
import com.example.medicalsupply.models.Category
import com.example.medicalsupply.models.Product
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class CategoryViewModel(private val category: Category) : ViewModel() {
    private val firestore = Firebase.firestore

    val offerProdLivedata: MutableLiveData<List<Product>> = MutableLiveData()
    val bestProdLivedata: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        fetchOfferProducts()
        fetchBestProducts()
    }

    private fun fetchOfferProducts() {
        viewModelScope.launch {
            firestore.collection(KeyProducts).whereEqualTo("category", category.category)
                .whereNotEqualTo("offerPercentage", null).get()
                .addOnSuccessListener {
                    val product = it.toObjects(Product::class.java)
                    offerProdLivedata.postValue(product)
                }.addOnFailureListener {
                    Log.e("TAG", "fetchOfferProducts: ${it.localizedMessage}")
                }
        }
    }

    private fun fetchBestProducts() {
        viewModelScope.launch {
            firestore.collection(KeyProducts).whereEqualTo("category", category.category)
                .whereEqualTo("offerPercentage", null).get()
                .addOnSuccessListener {
                    val product = it.toObjects(Product::class.java)
                    bestProdLivedata.postValue(product)
                }.addOnFailureListener {
                    Log.e("TAG", "fetchBestProducts: ${it.localizedMessage}")
                }
        }
    }
}