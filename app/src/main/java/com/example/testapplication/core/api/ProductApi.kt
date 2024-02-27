package com.example.testapplication.core.api

import com.example.testapplication.core.pojos.ResponseProductGeneric
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("/products")
    fun getProductsFromServer(): Call<ResponseProductGeneric>
}