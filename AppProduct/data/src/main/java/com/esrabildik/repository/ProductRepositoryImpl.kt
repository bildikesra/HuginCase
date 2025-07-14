package com.esrabildik.repository

import com.esrabildik.domain.model.Product
import com.esrabildik.domain.repository.ProductRepository
import com.esrabildik.domain.util.APIResult
import com.esrabildik.data.remote.ProductApi
import com.esrabildik.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {
    override fun getProducts(): Flow<APIResult<List<Product>>> =

        flow {
            emit(APIResult.Loading)
            val products = api.getProductList().products

            emit(APIResult.Success(products.map {
                it.toDomain()
            }))
        }.flowOn(Dispatchers.IO)
            .catch { err ->
                emit(APIResult.Error(err.message.toString()))
            }

    override fun getCategories(): Flow<APIResult<List<String>>> =
        flow {
            emit(APIResult.Loading)
            val categories = api.getCategoryList()

            emit(APIResult.Success(categories))
        }.flowOn(Dispatchers.IO)
            .catch { error ->
                emit(APIResult.Error(error.message.toString()))
            }

    override fun getProductById(id: String): Flow<APIResult<Product>> =
        flow {
            emit(APIResult.Loading)
            val product = api.getProductById(id)
            emit(APIResult.Success(product))
        }.flowOn(Dispatchers.IO)
            .catch { error ->
                emit(APIResult.Error(error.message.toString()))
            }

    override fun getCategoryName(categoryName: String): Flow<APIResult<List<Product>>> = flow {
        emit(APIResult.Loading)

        val response = api.getProductByCategory(categoryName)

        // Mapping
        val mappedProducts = response.products.map { it.toDomain() }

        emit(APIResult.Success(mappedProducts))
    }.flowOn(Dispatchers.IO).catch { error ->
        emit(APIResult.Error(error.message ?: "Unknown error"))
    }


}



