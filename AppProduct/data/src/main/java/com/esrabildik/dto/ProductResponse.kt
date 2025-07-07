package com.esrabildik.dto

data class ProductResponse(
    val products: List<ProductDTO>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

