package com.example.grocerymanagement.domain.serviceInterface

import java.io.File

interface ProductRepository {

    fun getProducts()
    fun deleteProductFromInventory(productId: String)
    fun addProductToInvent(name: String, barcode: String, description: String, quantity: String, imgFile: File)
}