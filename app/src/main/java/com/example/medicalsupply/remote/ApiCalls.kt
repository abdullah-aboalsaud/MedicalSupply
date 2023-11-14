package com.example.medicalsupply.remote

import com.example.medicalsupply.models.ModelDevices
import retrofit2.Call
import retrofit2.http.GET

interface ApiCalls {

    @GET("medical_equipment.json")
    fun getDevices(): Call<ModelDevices>


}