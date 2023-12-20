package com.example.medicalsupply.models

sealed class Category(val category: String) {
    object Medical : Category("Medical Students")
    object Dental : Category("Dental Students")
    object Pharmacy : Category("Pharmacy Students")
    object Nursing : Category("Nursing Students")

}