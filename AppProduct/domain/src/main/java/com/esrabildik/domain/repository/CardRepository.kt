package com.esrabildik.domain.repository

import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun getCardItems() : Flow<APIResult<List<CardItem>>>
    suspend fun addToCard(item : CardItem)
    suspend fun updateCard(item : CardItem)
    suspend fun deleteItem(item : CardItem)
    suspend fun clearCard()
}