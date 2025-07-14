package com.esrabildik.feature.orderScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esrabildik.domain.util.APIResult
import com.esrabildik.feature.orderScreen.component.BottomBar
import com.esrabildik.feature.orderScreen.component.ShoppingCard
import com.esrabildik.feature.orderScreen.viewmodel.CardViewModel
import com.esrabildik.feature.ui.theme.fontFamily


@Composable
fun ShoppingCardScreen(
    cardViewModel: CardViewModel = hiltViewModel()
) {
    val cardItemState by cardViewModel.cardItem.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        cardViewModel.getCardItems()
    }

    Scaffold(
        bottomBar = {
            if (cardItemState is APIResult.Success) {
                val cardItems = (cardItemState as APIResult.Success).data
                val totalPrice = cardItems.sumOf { it.price * it.quantity }

                BottomBar(totalPrice = totalPrice)
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)) {
            ShoppingBar()

            when (cardItemState) {
                is APIResult.Loading -> {
                    CircularProgressIndicator()
                }

                is APIResult.Error -> {
                    Log.e("CardItemState", "CardItemState Error")
                }

                is APIResult.Success -> {
                    val cardItems = (cardItemState as APIResult.Success).data
                    Log.d("CardItems", "$cardItems")

                    LazyColumn {
                        items(cardItems) { cardItem ->
                            ShoppingCard(cardItem, onDecrease = { item ->
                                if (item.quantity > 1) {
                                    val updateItem = item.copy(quantity = item.quantity - 1)
                                    cardViewModel.updateCard(updateItem)
                                } else {
                                    cardViewModel.deleteItem(item)
                                }
                            }, onIncrease = { item ->
                                val updateItem = item.copy(quantity = item.quantity + 1)
                                cardViewModel.updateCard(updateItem)
                            })
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ShoppingBar() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(
            text = "Shopping Cart",
            fontFamily = fontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}
