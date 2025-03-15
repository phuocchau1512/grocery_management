package com.example.grocerymanagement.domain.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.grocerymanagement.data.model.LoginRequest
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.ResponseBody

class LoginRepository(private val context: Context) {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    fun loginUser(user: LoginRequest) {
        RetrofitClient.instance.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val responseData = response.body()?.string()
                        Log.d("LoginRepository", "Response data: $responseData")

                        val jsonObject = JSONObject(responseData ?: "{}")
                        if (jsonObject.getBoolean("success")) {
                            val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString("userID", jsonObject.getString("userID"))
                                putString("userName", jsonObject.getString("userName"))
                                putString("email", jsonObject.getString("email"))
                                apply()
                            }
                            _loginStatus.value = true
                        } else {
                            Log.e("LoginRepository", "Login failed: ${jsonObject.getString("message")}")
                            _loginStatus.value = false
                        }
                    } catch (e: Exception) {
                        Log.e("LoginRepository", "Error parsing JSON response", e)
                        _loginStatus.value = false
                    }
                } else {
                    Log.e("LoginRepository", "Server returned error: ${response.code()}")
                    _loginStatus.value = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("LoginRepository", "Network request failed", t)
                _loginStatus.value = false
            }
        })
    }
}
