package com.esrabildik.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "card_item")
data class CardItemEntity(
    @PrimaryKey val cardId : Int,
    val title : String,
    val thumbnail : String,
    val category : String,
    val price : Double,
    val quantity : Int
)
