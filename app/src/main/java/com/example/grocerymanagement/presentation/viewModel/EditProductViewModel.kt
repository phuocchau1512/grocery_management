package com.example.grocerymanagement.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.grocerymanagement.data.repository.ProductRepositoryImpl
import java.io.File

class EditProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProductRepositoryImpl(application.applicationContext)
    val saveStatus: LiveData<Boolean> = repository.saveStatus

    fun addProductToInvent(name: String, barcode: String, description: String, quantity:String, imgFile: File) {
        repository.addProductToInvent(name, barcode, description, quantity , imgFile)
    }

}
