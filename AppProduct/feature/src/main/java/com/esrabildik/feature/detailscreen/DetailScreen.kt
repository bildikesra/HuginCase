package com.esrabildik.feature.detailscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.esrabildik.domain.mapper.toCardItem
import com.esrabildik.domain.model.CardItem
import com.esrabildik.domain.model.Product
import com.esrabildik.domain.model.Review
import com.esrabildik.domain.util.APIResult
import com.esrabildik.feature.R
import com.esrabildik.feature.homescreen.viewmodel.ProductViewModel
import com.esrabildik.feature.orderScreen.viewmodel.CardViewModel
import com.esrabildik.feature.ui.theme.OrangeCard
import com.esrabildik.feature.ui.theme.backgroundCard
import com.esrabildik.feature.ui.theme.cardColor
import com.esrabildik.feature.ui.theme.fontFamily

@Composable
fun ProductDetailScreen(
    productId: String,
    cardViewModel: CardViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel()
) {

    val productDetailState = productViewModel.productDetail.collectAsStateWithLifecycle()

    var isComment by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(productId) {
        productViewModel.fetchProductById(productId)
    }

    when (val result = productDetailState.value) {
        is APIResult.Loading ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        is APIResult.Error -> Text("Hata: ${result.message}")
        is APIResult.Success -> {
            val product = result.data
            Scaffold(
                bottomBar = {
                    AddButton(
                        cardItem = product.toCardItem(),
                        viewModel = cardViewModel
                    )
                }
            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        ProductImage(product)
                    }

                    if (!isComment) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ProductTitle(product)
                            }
                        }

                        item {
                            ProductContent(product)
                        }
                        item {
                            ReviewList(product.reviews) {
                                isComment = it
                            }
                        }


                    } else {

                        item {
                            Text(
                                text = "Product Reviews",
                                fontSize = 17.sp,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Start
                            )
                        }

                        items(product.reviews) { review ->
                            ReviewItem(review)

                        }
                        item {
                            CloseReviewButton() {
                                isComment = it
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ProductImage(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f)
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = product.images.firstOrNull(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductTitle(product: Product) {
    Text(
        "${product.brand}", modifier = Modifier
            .padding(start = 10.dp),
        fontSize = 19.sp,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Start
    )
    Image(
        painter = painterResource(id = R.drawable.baseline_star_24),
        contentDescription = "star",
        modifier = Modifier.size(26.dp)
    )
    Text(
        text = product.rating.toString(),
        modifier = Modifier.padding(4.dp),
        fontSize = 17.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray
    )
}

@Composable
fun ProductContent(product: Product) {
    Text(
        product.title, modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp),
        fontSize = 22.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Start
    )
    Text(
        product.description, modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp),
        fontSize = 19.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        color = Color.DarkGray,
        textAlign = TextAlign.Start
    )

}

@Composable
fun CloseReviewButton(
    isComment: (Boolean) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .size(50.dp)
            .clickable {
                isComment(false)
            },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = OrangeCard)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

    }
}

@Composable
fun ReviewList(
    reviews: List<Review>,
    isComment: (Boolean) -> Unit
) {
    if (reviews.isEmpty()) {
        Text("Henüz yorum yok.", modifier = Modifier.padding(16.dp))
    } else {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 5.dp, bottom = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Product Reviews",
                        fontSize = 17.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = backgroundCard,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        modifier = Modifier.clickable {
                            isComment(true)
                        },
                        text = "View all",
                        fontSize = 17.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        color = backgroundCard,
                        textAlign = TextAlign.End
                    )
                }


                ReviewItem(reviews.first())
            }


        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp, top = 8.dp, end = 2.dp, bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = review.reviewerName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    fontFamily = fontFamily,
                    maxLines = 1,
                    color = Color(0xFF29242F),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = review.rating.toString(),
                    color = Color(0xFF313334),
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.comment,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.date.take(10), // YYYY-MM-DD
                fontWeight = FontWeight.Normal,
                fontFamily = fontFamily,
                color = Color.Gray
            )

        }

    }

}


@Composable
fun AddButton(cardItem: CardItem, viewModel: CardViewModel) {

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
                text = "${cardItem.price}$", modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(10.dp)
                    .clickable {
                        viewModel.addToCard(item = cardItem)
                    }, //TODO : SEPETE GİT
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundCard)
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "ShoppingCart",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Add to Cart",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}





