package com.example.medicalsupply.showdata


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicalsupply.KeyProducts
import com.example.medicalsupply.adapters.AdapterDevices
import com.example.medicalsupply.databinding.ActivityHomeBinding
import com.example.medicalsupply.models.ModelDevices
import com.google.firebase.database.FirebaseDatabase


class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val adapterDevices: AdapterDevices by lazy { AdapterDevices() }

    private lateinit var viewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.getDevices()
        viewModel.deviceLiveData.observe(this, Observer{

            adapterDevices.deviceList = it
            binding.recyclerDevices.adapter = adapterDevices

            adapterDevices
                .setOnclick {id->
                    val intent = Intent(this@HomeActivity, DetailsActivity::class.java)
                    intent.putExtra("deviceCode", id)
                    startActivity(intent)
                }

        })


    }
}