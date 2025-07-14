package com.esrabildik.data.remote

import com.esrabildik.domain.model.Product
import com.esrabildik.dto.ProductDTO
import com.esrabildik.dto.ProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("/products")
    suspend fun getProductList(): ProductResponse

    @GET("/products/category-list")
    suspend fun getCategoryList() : List<String>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: String): Product

    @GET("/products/category/{category_name}")
    suspend fun getProductByCategory(@Path("category_name") categoryName : String) :  ProductResponse

}
