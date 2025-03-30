package com.example.grocerymanagement.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.grocerymanagement.domain.model.Product
import com.example.grocerymanagement.data.repository.ProductRepositoryImpl


class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepositoryImpl(application.applicationContext)
    val product: LiveData<List<Product>> = repository.products

    fun getProductInInvent() {
        repository.getProducts()
    }

    fun deleteProductInInvent(productId:String){
        repository.deleteProductFromInventory(productId)
    }


}