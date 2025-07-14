package com.esrabildik.feature.orderScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.esrabildik.domain.model.CardItem
import com.esrabildik.feature.R
import com.esrabildik.feature.ui.theme.backgroundCard
import com.esrabildik.feature.ui.theme.fontFamily

@Composable
fun ShoppingCard(
    cardItem: CardItem,
    onIncrease: (CardItem) -> Unit,
    onDecrease: (CardItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            AsyncImage(
                model = cardItem.thumbnail,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .alignByBaseline() // Dikey hizalama
            ) {
                Text(
                    text = cardItem.title,
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = cardItem.category.uppercase(),
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )

                Row(modifier = Modifier.padding(8.dp)) {
                    ElevatedCard(
                        modifier = Modifier.size(50.dp),
                        onClick = { onDecrease(cardItem) }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                ),
                            contentDescription = "decrease"
                        )
                    }

                    Text(
                        cardItem.quantity.toString(),
                        modifier = Modifier
                            .padding(12.dp),
                        fontSize = 22.sp
                    )
                    ElevatedCard(
                        modifier = Modifier.size(50.dp),
                        onClick = { onIncrease(cardItem) }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                ),
                            contentDescription = "increase"
                        )
                    }
                }
                Text(
                    text = "Total: ${cardItem.price * cardItem.quantity} ₺",
                    fontFamily = fontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.End
                )
            }

        }
    }
}

@Composable
fun BottomBar(totalPrice: Double) {
    Surface(
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Total: $totalPrice ₺",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                shape = RoundedCornerShape(6.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundCard)
            ) {
                Text("Buy Now",
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(14.dp))
            }
        }
    }
}















