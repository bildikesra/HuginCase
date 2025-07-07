package com.esrabildik.remote

import com.esrabildik.dto.ProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ProductApi {
    @GET("/products")
    suspend fun getProductList(): ProductResponse

    @GET("/products/category-list")
    suspend fun getCategoryList() : List<String>
}
