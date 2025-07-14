package com.esrabildik.repository

import com.esrabildik.data.local.dao.CardDao
import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.repository.CardRepository
import com.esrabildik.domain.util.APIResult
import com.esrabildik.mapper.toDomain
import com.esrabildik.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val dao: CardDao
) : CardRepository {

    override suspend fun getCardItems(): Flow<APIResult<List<CardItem>>> = flow {

        try {
            dao.getAllCardItem().collect { cardList ->
                val cardList = cardList.map { it.toDomain() }
                emit(APIResult.Success(cardList))
            }
        } catch (e : Exception){
            emit(APIResult.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun addToCard(item: CardItem) {
       dao.insertCardItem(item.toEntity())
    }

    override suspend fun updateCard(item: CardItem) {
        dao.updateCardItem(item.toEntity())

    }

    override suspend fun deleteItem(item: CardItem) {
       dao.deleteCardItem(item.toEntity())
    }

    override suspend fun clearCard() {
        dao.clearCard()
    }
}