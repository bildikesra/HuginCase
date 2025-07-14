package com.esrabildik.domain.usecase.cardUsecase

import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.repository.CardRepository
import javax.inject.Inject

class UpdateCardUsecase @Inject constructor(
    val repository: CardRepository
) {
    suspend operator fun invoke(item : CardItem) {
        repository.updateCard(item)
    }
}