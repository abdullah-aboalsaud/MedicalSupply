package com.example.medicalsupply.product.models

data class UserReview(
    val user: String,
    val rate: Int,
    val review: String? = null
)
