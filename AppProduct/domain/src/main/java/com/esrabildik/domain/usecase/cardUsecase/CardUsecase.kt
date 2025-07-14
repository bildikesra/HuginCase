package com.esrabildik.domain.usecase.cardUsecase

data class CardUsecase(
    val getCardUsecase: GetCardUsecase,
    val addToCard: AddCardUsecase,
    val updateCard: UpdateCardUsecase,
    val deleteFromCard: DeleteCardUsecase,
    val clearCard: ClearCardUsecase
)
