package com.example.grocerymanagement.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.grocerymanagement.data.model.Product
import com.example.grocerymanagement.domain.repository.ProductRepository
import java.io.File


class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepository(application.applicationContext)
    val product: LiveData<List<Product>> = repository.products

    fun addProductToInvent() {
        repository.getProducts()
    }

}