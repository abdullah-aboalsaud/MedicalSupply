package com.example.medicalsupply.showdata


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicalsupply.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val deviceCode = intent.getStringExtra("deviceCode")

        viewModel.getDevices()
        viewModel.deviceLiveData.observe(this, Observer {
            for (i in it.indices) {
                if (it[i].id== deviceCode) {
                    binding.textViewName.text=it[i].name
                    binding.textViewCategory.text=it[i].category
                    binding.textViewDescription .text="description:\n${it[i].description}"
                    Glide.with(binding.root.context)
                        .load(it[i].imageUrl)
                        .into(binding.imageView)
                    binding.textViewPrice.text=it[i].price+" EGP"

                }
            }

        })


    }
}