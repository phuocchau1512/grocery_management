package com.example.grocerymanagement.domain.model

data class Product(
    val id: Int,
    val name: String,
    val barcode: String,
    val img: String, // Đường dẫn ảnh
    val description: String,
    val quantity: Int
)
