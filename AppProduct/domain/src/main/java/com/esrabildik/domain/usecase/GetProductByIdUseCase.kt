package com.esrabildik.domain.usecase

import com.esrabildik.domain.model.Product
import com.esrabildik.domain.repository.ProductRepository
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow

class GetProductByIdUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Flow<APIResult<Product>> {
        return repository.getProductById(id)
    }
}