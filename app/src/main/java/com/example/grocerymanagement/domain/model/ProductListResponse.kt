package com.example.grocerymanagement.domain.model

import com.example.grocerymanagement.data.model.Product

data class ProductResponse(
    val success: Boolean,
    val products: List<Product>
)
