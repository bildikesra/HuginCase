package com.esrabildik.feature.orderScreen.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.repository.CardRepository
import com.esrabildik.domain.usecase.cardUsecase.CardUsecase
import com.esrabildik.domain.util.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardUsecase: CardUsecase
) : ViewModel() {

    private val _cardItems = MutableStateFlow<APIResult<List<CardItem>>>(APIResult.Loading)
    val cardItem : StateFlow<APIResult<List<CardItem>>> = _cardItems

    fun getCardItems(){
        viewModelScope.launch {
            cardUsecase.getCardUsecase().collectLatest { result ->
                _cardItems.value = result
            }
        }
    }

    fun addToCard(item: CardItem) {
        viewModelScope.launch {
            try {
                cardUsecase.addToCard(item)
                getCardItems()
                Log.d("CardItem" , item.title)
            } catch (e: Exception) {
                // Hata yönetimi yapılabilir
            }
        }
    }

    fun updateCard(item: CardItem) {
        viewModelScope.launch {
            try {
                cardUsecase.updateCard(item)
                getCardItems()
            } catch (e: Exception) {
                // Hata yönetimi yapılabilir
            }
        }
    }

    fun deleteItem(item: CardItem) {
        viewModelScope.launch {
            try {
                cardUsecase.deleteFromCard(item)
                getCardItems()
            } catch (e: Exception) {
                // Hata yönetimi yapılabilir
            }
        }
    }

    fun clearCard() {
        viewModelScope.launch {
            try {
                cardUsecase.clearCard()
                getCardItems()
            } catch (e: Exception) {
                // Hata yönetimi yapılabilir
            }
        }
    }
}