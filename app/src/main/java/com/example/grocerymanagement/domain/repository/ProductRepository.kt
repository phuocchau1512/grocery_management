package com.example.grocerymanagement.domain.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProductRepository(private val context: Context) {

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> get() = _saveStatus

    fun addProductToInvent(name: String, barcode: String, description: String, quantity: String, imgFile: File) {
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("userID", "") ?: "" // Lấy user_id từ SharedPreferences

        val userIdBody = RequestBody.create(MediaType.parse("text/plain"), userId)
        val nameBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val barcodeBody = RequestBody.create(MediaType.parse("text/plain"), barcode)
        val descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description)
        val quantityBody = RequestBody.create(MediaType.parse("text/plain"), quantity) // Chuyển Int thành String

        val requestFile = RequestBody.create(MediaType.parse("image/*"), imgFile)
        val imagePart = MultipartBody.Part.createFormData("img", imgFile.name, requestFile)

        RetrofitClient.productApi.addProductToInvent(userIdBody, nameBody, barcodeBody, descriptionBody, quantityBody, imagePart)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val responseBody = response.body()?.string()
                    val errorBody = response.errorBody()?.string()

                    Log.d("AddProduct", "Server Response: $responseBody")
                    Log.d("AddProduct", "Error Response: $errorBody")

                    _saveStatus.value = response.isSuccessful
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("AddProduct", "Request Failed: ${t.message}")
                    _saveStatus.value = false
                }
            })
    }




}