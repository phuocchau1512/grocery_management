package com.example.grocerymanagement.presentation.viewModel

import androidx.lifecycle.LiveData
import com.example.grocerymanagement.data.model.LoginRequest
import com.example.grocerymanagement.domain.repository.LoginRepository

import android.app.Application
import androidx.lifecycle.AndroidViewModel


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoginRepository(application.applicationContext)
    val loginStatus: LiveData<Boolean> = repository.loginStatus

    fun loginUser(user: LoginRequest) {
        repository.loginUser(user)
    }
}


