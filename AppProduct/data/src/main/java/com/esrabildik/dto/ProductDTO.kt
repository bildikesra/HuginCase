package com.esrabildik.dto

import com.esrabildik.domain.model.Dimensions
import com.esrabildik.domain.model.Meta
import com.esrabildik.domain.model.Review

data class ProductDTO(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val brand: String,
    val stock: Int,
    val tags: List<String>,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String
)

