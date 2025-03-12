package com.example.grocerymanagement.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.grocerymanagement.data.model.CreateUserReq
import com.example.grocerymanagement.domain.repository.RegisterRepository

class RegisterViewModel : ViewModel() {
    private val repository = RegisterRepository()
    val registerStatus: LiveData<Boolean> = repository.registerStatus

     fun registerUser(user: CreateUserReq) {
        repository.registerUser(user)
    }
}