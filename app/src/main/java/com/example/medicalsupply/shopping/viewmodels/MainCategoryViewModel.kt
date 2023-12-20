package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.core.KeyProducts
import com.example.medicalsupply.models.Product
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch


class MainCategoryViewModel : ViewModel() {
    private val db = Firebase.firestore

    val specialProductsLivedata: MutableLiveData<List<Product>> = MutableLiveData()
    val bestDealsProductsLivedata: MutableLiveData<List<Product>> = MutableLiveData()
    val bestProductsLiveData: MutableLiveData<List<Product>> = MutableLiveData()

    fun fetchSpecialProducts() {
        viewModelScope.launch {
            db.collection(KeyProducts).whereEqualTo("category", "Special Products")
                .get()
                .addOnSuccessListener { result ->
                    val specialProductList = result.toObjects(Product::class.java)
                    specialProductsLivedata.postValue(specialProductList)
                }.addOnFailureListener {
                    Log.e("TAG", "fetchSpecialProducts: ${it.localizedMessage}")
                }
        }

    }
    fun fetchBestDeals(){
        viewModelScope.launch {
            db.collection(KeyProducts).whereEqualTo("category","Best Deals")
                .get()
                .addOnSuccessListener {result->
                    val bestDealProducts = result.toObjects(Product::class.java)
                    bestDealsProductsLivedata.postValue(bestDealProducts)
                }.addOnFailureListener {
                    Log.e("TAG", "fetchBestDeals: ${it.localizedMessage}" )
                }
        }
    }

    fun fetchBestProducts(){
        viewModelScope.launch {
            db.collection(KeyProducts).whereEqualTo("category","Best Products")
                .get()
                .addOnSuccessListener {result->
                    val bestProductsResult = result.toObjects(Product::class.java)
                    bestProductsLiveData.postValue(bestProductsResult)
                }.addOnFailureListener {
                    Log.e("TAG", "fetchBestProducts: ${it.localizedMessage}" )
                }
        }
    }

}