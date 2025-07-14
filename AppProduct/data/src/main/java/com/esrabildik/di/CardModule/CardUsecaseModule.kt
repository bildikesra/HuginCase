package com.esrabildik.di.CardModule

import com.esrabildik.domain.repository.CardRepository
import com.esrabildik.domain.usecase.cardUsecase.AddCardUsecase
import com.esrabildik.domain.usecase.cardUsecase.CardUsecase
import com.esrabildik.domain.usecase.cardUsecase.ClearCardUsecase
import com.esrabildik.domain.usecase.cardUsecase.DeleteCardUsecase
import com.esrabildik.domain.usecase.cardUsecase.GetCardUsecase
import com.esrabildik.domain.usecase.cardUsecase.UpdateCardUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CardUseCaseModule {

    @Provides
    fun provideCardUseCases(repository: CardRepository): CardUsecase {
        return CardUsecase(
            addToCard = AddCardUsecase(repository),
            updateCard = UpdateCardUsecase(repository),
            deleteFromCard = DeleteCardUsecase(repository),
            clearCard = ClearCardUsecase(repository),
            getCardUsecase = GetCardUsecase(repository)
        )
    }
}
