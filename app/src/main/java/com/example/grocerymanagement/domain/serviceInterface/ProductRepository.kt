package com.example.grocerymanagement.domain.serviceInterface

import java.io.File

interface ProductRepository {


    fun addProductToInvent(name: String, barcode: String, description: String, quantity: String, note: String, imgFile: File)
    fun editProductToInvent(productId: Int, name: String, barcode: String, description: String, quantity: String, note: String, imgFile: File?)
    fun getProducts()
    fun deleteProductFromInventory(productId: String)
}