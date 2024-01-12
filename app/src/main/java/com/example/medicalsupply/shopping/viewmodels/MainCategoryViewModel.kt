package com.example.medicalsupply.shopping.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.firebase.FirebaseCommon
import com.example.medicalsupply.models.CartProduct
import com.example.medicalsupply.utils.KeyProducts
import com.example.medicalsupply.models.Product
import com.example.medicalsupply.utils.Resource
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainCategoryViewModel : ViewModel() {
    private val db = Firebase.firestore


    private val _specialProductsSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProductsSF: StateFlow<Resource<List<Product>>> = _specialProductsSF

    private val _bestDealsSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealsSF: StateFlow<Resource<List<Product>>> = _bestDealsSF

    private val _bestProductsSF = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProductsSF: StateFlow<Resource<List<Product>>> = _bestProductsSF

    fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialProductsSF.emit(Resource.Loading())
        }
        db.collection(KeyProducts).whereEqualTo("category", "Special Products")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val specialProductList = result.toObjects(Product::class.java)
                    _specialProductsSF.emit(Resource.Success(specialProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProductsSF.emit(Resource.Error(it.message.toString()))
                }
            }


    }

    fun fetchBestDeals() {
        viewModelScope.launch {
            _bestDealsSF.emit(Resource.Loading())
        }
        db.collection(KeyProducts).whereEqualTo("category", "Best Deals")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val bestDealProducts = result.toObjects(Product::class.java)
                    _bestDealsSF.emit(Resource.Success(bestDealProducts))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun fetchBestProducts() {
        viewModelScope.launch {
            _bestProductsSF.emit(Resource.Loading())
        }
        db.collection(KeyProducts).whereEqualTo("category", "Best Products")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val bestProductsResult = result.toObjects(Product::class.java)
                    _bestProductsSF.emit(Resource.Success(bestProductsResult))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProductsSF.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}