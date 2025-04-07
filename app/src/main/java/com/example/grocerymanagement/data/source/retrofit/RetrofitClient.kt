package com.example.grocerymanagement.data.source.retrofit

import com.example.grocerymanagement.data.source.api.AuthApi
import com.example.grocerymanagement.data.source.api.ProductApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // sửa địa chỉ ip hiện tại của máy
    private const val BASE_URL = "http://192.168.1.22/api_grocery/"

    fun getBaseUrl(): String { return BASE_URL}

    val instance: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    val productApi: ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }
}
