package com.example.grocerymanagement.data.source.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ShoppingApi {

    @FormUrlEncoded
    @POST("shopping/add_new_list.php")
    fun addNewList(
        @Field("user_id") userId: String,
        @Field("name") note: String
    ): Call<ResponseBody>

}