package com.esrabildik.mapper

import com.esrabildik.domain.model.Product
import com.esrabildik.dto.ProductDTO


fun ProductDTO.toDomain() : Product {
    return Product (
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail,
        stock = stock,
        rating = rating,
        brand = brand,
        images = images,
        reviews = reviews,
        category = category
    )
}

