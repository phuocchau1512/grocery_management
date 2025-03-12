package com.example.grocerymanagement.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.grocerymanagement.data.model.CreateUserReq
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> get() = _registerStatus

    fun registerUser(user: CreateUserReq) {

        RetrofitClient.instance.signUp(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("RegisterRepository", "API đăng ký thành công: ${response.body()?.string()}")
                    _registerStatus.value = true
                } else {
                    Log.e("RegisterRepository", "API lỗi: ${response.errorBody()?.string()}")
                    _registerStatus.value = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("RegisterRepository", "Lỗi kết nối API: ${t.message}")
                _registerStatus.value = false
            }
        })
    }


}
