package com.example.grocerymanagement.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import com.example.grocerymanagement.domain.serviceInterface.ShoppingRepository
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingRepositoryImpl(private val context: Context): ShoppingRepository {

    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> get() = _saveStatus

    override fun addNewList(name: String) {
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("userID", "") ?: ""

        RetrofitClient.shoppingApi.addNewList(userId, name)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val responseBody = response.body()?.string()
                    val errorBody = response.errorBody()?.string()

                    Log.d("AddList", "Server Response: $responseBody")
                    Log.d("AddList", "Error Response: $errorBody")

                    try {
                        val jsonResponse = JSONObject(responseBody ?: "{}")
                        val message = jsonResponse.optString("message", "Có lỗi xảy ra")

                        if (jsonResponse.optBoolean("success", false)) {
                            Toast.makeText(context, "Tạo danh sách thành công", Toast.LENGTH_SHORT).show()
                            // Cập nhật LiveData/UI nếu cần
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("AddList", "Lỗi xử lý JSON: ${e.message}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("AddList", "Request Failed: ${t.message}")
                    Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show()
                }
            })
    }


}