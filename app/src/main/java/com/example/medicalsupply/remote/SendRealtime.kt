package com.example.medicalsupply.remote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.medicalsupply.KeyProducts
import com.example.medicalsupply.R
import com.example.medicalsupply.models.ModelDevices
import com.google.firebase.database.FirebaseDatabase

class SendRealtime : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference(KeyProducts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_realtime)

        val id = myRef.push().key
        val modelDevices = ModelDevices(
            "medical devices",
            "",
            id!!,
            "",
            "r",
            ""
        )

        myRef.child(id).setValue(modelDevices)
            .addOnSuccessListener {
                Log.e("TAG", "addOnSuccessListener: ")
            }.addOnFailureListener {
                Log.e("TAG", "addOnFailureListener: ")
            }


    }
}