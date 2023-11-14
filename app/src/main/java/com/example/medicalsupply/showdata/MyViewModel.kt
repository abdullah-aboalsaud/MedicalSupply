package com.example.medicalsupply.showdata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.medicalsupply.KeyProducts
import com.example.medicalsupply.models.ModelDevices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MyViewModel: ViewModel() {
    val deviceLiveData =MutableLiveData<ArrayList<ModelDevices>>()

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(KeyProducts)

     var deviceList = ArrayList<ModelDevices>()


    fun getDevices(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1 in snapshot.children) {
                   val product = snapshot1.getValue(ModelDevices::class.java)

                    deviceList.add(product!!)
                }
                deviceLiveData.value=deviceList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "onCancelled ValueEventListener ")
            }
        })

    }

}

