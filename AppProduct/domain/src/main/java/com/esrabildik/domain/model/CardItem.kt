package com.esrabildik.domain.model


data class CardItem(
    val cardId: Int,
    val title: String,
    val category : String,
    val thumbnail: String,
    val price: Double,
    var quantity: Int = 1
)