package com.esrabildik.feature.state

import com.esrabildik.domain.model.Product

data class ProductState(
    val isLoading : Boolean = false,
    val products : List<Product> = emptyList(),
    val error : String? = null
)
