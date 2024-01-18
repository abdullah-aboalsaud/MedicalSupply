package com.example.medicalsupply.order

import com.example.medicalsupply.models.Address
import com.example.medicalsupply.models.CartProduct

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val products: List<CartProduct>,
    val address: Address
)
