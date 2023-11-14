package com.example.medicalsupply.remote

import com.example.medicalsupply.models.ModelDevices
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitConnection {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.fda.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiCalls = retrofit.create(ApiCalls::class.java)

    fun getDevices(): Call<ModelDevices>{
        return apiCalls.getDevices()
    }

}