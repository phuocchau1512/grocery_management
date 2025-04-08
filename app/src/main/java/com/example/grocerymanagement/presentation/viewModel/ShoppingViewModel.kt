package com.example.grocerymanagement.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.grocerymanagement.data.repository.ProductRepositoryImpl
import com.example.grocerymanagement.data.repository.ShoppingRepositoryImpl
import java.io.File

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShoppingRepositoryImpl(application.applicationContext)
    val saveStatus: LiveData<Boolean> = repository.saveStatus

    fun addNewList(name: String){
        repository.addNewList(name)
    }

}