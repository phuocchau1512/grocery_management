package com.example.grocerymanagement.data.source.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProductApi {
    @Multipart
    @POST("products/save_product.php")
    fun addProductToInvent(
        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("barcode") barcode: RequestBody,
        @Part("description") description: RequestBody,
        @Part("quantity") quantity: RequestBody,
        @Part img: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("products/get_list_inventory.php")
    fun getListProductInvent(
        @Query("user_id") userId: String
    ): Call<ResponseBody>


}