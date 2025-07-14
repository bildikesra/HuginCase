package com.esrabildik.mapper

import com.esrabildik.data.local.entity.CardItemEntity
import com.esrabildik.domain.model.CardItem

fun CardItemEntity.toDomain() : CardItem {
    return CardItem(
        cardId = cardId,
        title = title,
        thumbnail = thumbnail,
        price = price,
        category = category,
        quantity = quantity
    )
}

fun CardItem.toEntity() : CardItemEntity {
    return CardItemEntity(
        cardId = cardId,
        title = title,
        thumbnail = thumbnail,
        price = price,
        category = category,
        quantity = quantity
    )
}