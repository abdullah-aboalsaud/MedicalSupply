package com.example.medicalsupply.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.medicalsupply.auth.AuthActivity
import com.example.medicalsupply.databinding.ActivitySplashBinding
import com.example.medicalsupply.product.ProductsActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            if(auth.uid==null){
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashActivity, ProductsActivity::class.java))
                finish()
            }
        }, 2000)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.progress.visibility = View.GONE
        _binding = null
    }
}