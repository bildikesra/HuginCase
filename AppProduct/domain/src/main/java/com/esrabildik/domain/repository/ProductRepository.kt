package com.esrabildik.domain.repository


import com.esrabildik.domain.model.Product
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<APIResult<List<Product>>>
    fun getCategories(): Flow<APIResult<List<String>>>
    fun getProductById(id: String): Flow<APIResult<Product>>
    fun getCategoryName(categoryName: String): Flow<APIResult<List<Product>>>
}