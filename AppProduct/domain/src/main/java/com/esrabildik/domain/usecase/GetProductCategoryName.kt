package com.esrabildik.domain.usecase

import com.esrabildik.domain.model.Product
import com.esrabildik.domain.repository.ProductRepository
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow

class GetProductCategoryName(val repository: ProductRepository) {
     operator fun invoke(categoryName : String) : Flow<APIResult<List<Product>>> {
        return repository.getCategoryName(categoryName)
    }
}