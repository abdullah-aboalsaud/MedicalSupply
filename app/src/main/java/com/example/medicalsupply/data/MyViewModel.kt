package com.example.medicalsupply.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalsupply.models.ModelProduct



class MyViewModel : ViewModel() {
    var productLiveData : MutableLiveData<ArrayList<ModelProduct>>
    private val repo = Repo()
    init {
        productLiveData=repo.productLiveData
    }
    fun getDevices() {
        repo.getProducts()
    }

    /** reset password */



}

