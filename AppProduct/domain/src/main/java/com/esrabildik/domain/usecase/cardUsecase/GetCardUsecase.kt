package com.esrabildik.domain.usecase.cardUsecase

import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.repository.CardRepository
import com.esrabildik.domain.util.APIResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardUsecase @Inject constructor(
    val repository: CardRepository
) {
    suspend operator fun invoke(): Flow<APIResult<List<CardItem>>> {
        return repository.getCardItems()
    }
}