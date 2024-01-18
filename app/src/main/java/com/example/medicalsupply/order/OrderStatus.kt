package com.example.medicalsupply.order

sealed class OrderStatus(val status: String) {
    object Ordered : OrderStatus("ordered")
    object Canceled : OrderStatus("Canceled")
    object Confirmed : OrderStatus("Confirmed")
    object Shipped : OrderStatus("Shipped")
    object Delivered : OrderStatus("Delivered")
    object Returned : OrderStatus("Returned")

}