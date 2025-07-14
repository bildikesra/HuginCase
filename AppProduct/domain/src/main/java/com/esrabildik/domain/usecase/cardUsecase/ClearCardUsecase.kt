package com.esrabildik.domain.usecase.cardUsecase

import com.esrabildik.domain.repository.CardRepository
import javax.inject.Inject

class ClearCardUsecase @Inject constructor(
    val repository: CardRepository
) {
    suspend operator fun invoke(){
        repository.clearCard()
    }
}