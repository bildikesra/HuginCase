package com.esrabildik.domain.usecase

import com.esrabildik.domain.repository.ProductRepository
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUsecase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke() : Flow<APIResult<List<String>>> {
        return repository.getCategories()
    }
}