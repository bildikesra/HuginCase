package com.esrabildik.domain.model

data class Product(
    val id : Int,
    val title : String,
    val description : String,
    val category : String,
    val price : Double,
    val rating : Double,
    val brand  : String?,
    val stock : Int,
    val reviews: List<Review>,
    val images: List<String>,
    val thumbnail: String
)
