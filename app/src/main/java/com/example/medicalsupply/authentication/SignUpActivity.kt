package com.example.medicalsupply.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalsupply.showdata.HomeActivity
import com.example.medicalsupply.KeyUser
import com.example.medicalsupply.databinding.ActivitySignUpBinding
import com.example.medicalsupply.models.ModelUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private val auth = FirebaseAuth.getInstance()
    private val reference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            validate(email, password, name)
        }

    }

    private fun validate(email: String, password: String, name: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "email required"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "password required"
        } else if (password.length < 5) {
            binding.etPassword.error = "password should not be less than 5"
        } else if (name.isEmpty()) {
            binding.etName.error = "name required"
        } else {
            register(name, email, password)
        }
    }

    private fun register(name: String, email: String, password: String) {
        binding.progress.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let { user ->
                    sendDataToRealTime(name, email, user.uid)
                }

            }.addOnFailureListener {
                binding.progress.visibility = View.GONE
                Toast.makeText(
                    this@SignUpActivity,
                    "failed: ${it.localizedMessage}", Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun sendDataToRealTime(name: String, email: String, userId: String) {
        reference.child(KeyUser)
            .child(userId).setValue(ModelUser(email, name, userId))
            .addOnSuccessListener {
                binding.progress.visibility = View.GONE
                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                finish()
            }.addOnFailureListener {
                binding.progress.visibility = View.GONE
                Toast.makeText(
                    this@SignUpActivity,
                    "sent data to realtime Failed: " + it.localizedMessage, Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "sendDataToRealTime: ${it.localizedMessage}")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
