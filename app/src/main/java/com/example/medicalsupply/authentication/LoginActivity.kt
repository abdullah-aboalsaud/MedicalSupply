package com.example.medicalsupply.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalsupply.showdata.HomeActivity
import com.example.medicalsupply.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding?=null
    private val binding get()=_binding!!

    private lateinit var ref: DatabaseReference
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ref = FirebaseDatabase.getInstance().reference


        binding.btnGoToSignUp.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))

        })

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPass.text.toString().trim()
            validate(email, password)


            binding.etEmail.text = null
            binding.etPass.text = null
        })

    }

    private fun validate(email: String, password: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "name is required"
        } else if (password.isEmpty()) {
            binding.etPass.error = "password is required"
        } else if (password.length < 6) {
            binding.etPass.error = "password should not be less than 6"
        } else {
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        binding.progress.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            binding.progress.visibility = View.GONE
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()

        }.addOnFailureListener {
            binding.progress.visibility = View.GONE
            Toast.makeText(
                this@LoginActivity, "failed: " + it.localizedMessage,
                Toast.LENGTH_LONG
            ).show()
            Log.e("TAG", "login: ${it.localizedMessage}")
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}


//            var id = ref.push().key
//            ref.child("user_information").child(id.toString())
//                .setValue(
//                    ModelUser(
//                        binding.etEmail.text.toString(),
//                        binding.etPass.text.toString()
//                    )
//                )