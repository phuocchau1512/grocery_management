package com.example.grocerymanagement.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.grocerymanagement.data.model.CreateUserReq
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {

    private val _registerStatus = MutableLiveData<Pair<Boolean, String>>() // Trả về cả trạng thái & thông báo lỗi
    val registerStatus: LiveData<Pair<Boolean, String>> get() = _registerStatus

    fun registerUser(user: CreateUserReq) {
        RetrofitClient.instance.signUp(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val responseBody = response.body()?.string()
                    val jsonObject = JSONObject(responseBody ?: "")

                    val success = jsonObject.getBoolean("success")
                    val message = jsonObject.getString("message")


                    _registerStatus.postValue(Pair(success, message)) // Truyền thông báo lỗi về UI

                } catch (e: Exception) {
                    _registerStatus.postValue(Pair(false, "Lỗi khi xử lý dữ liệu phản hồi"))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _registerStatus.postValue(Pair(false, "Lỗi kết nối, vui lòng thử lại"))
            }
        })
    }
}

