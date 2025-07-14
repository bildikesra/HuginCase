package com.esrabildik.domain.mapper


import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.model.Product



fun Product.toCardItem() : CardItem {
    return CardItem(
        cardId = id,
        title = title,
        thumbnail = thumbnail,
        price = price,
        category = category,
        quantity = 1
    )
}