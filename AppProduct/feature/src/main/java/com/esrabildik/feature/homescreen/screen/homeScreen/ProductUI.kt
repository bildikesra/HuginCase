package com.esrabildik.feature.homescreen.screen.homeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esrabildik.domain.model.Product
import com.esrabildik.feature.R
import com.esrabildik.feature.homescreen.component.CategoryCard
import com.esrabildik.feature.homescreen.component.ProductCard
import com.esrabildik.feature.homescreen.component.ProductSearchBar
import com.esrabildik.feature.homescreen.viewmodel.ProductViewModel
import com.esrabildik.feature.homescreen.viewmodel.UIState
import com.esrabildik.feature.ui.theme.fontFamily


@ExperimentalMaterial3Api
@Composable
fun ProductScreen(
    viewmodel: ProductViewModel = hiltViewModel(),
    productClicked: (Int) -> Unit,
    shoppingCardClicked: () -> Unit
) {
    val productState by viewmodel.uiState.collectAsStateWithLifecycle()
    val categoryState by viewmodel.categoryState.collectAsStateWithLifecycle()

    val searchQuery by viewmodel.searchQuery.collectAsStateWithLifecycle()

    val selectedCategory by viewmodel.selectedCategory.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {


        ProductSearchBar(
            query = searchQuery,
            modifier = Modifier.fillMaxWidth(),
            onQueryChanged = { viewmodel.onSearchQueryChanged(it) }
        )

        ShoppingBar(shoppingCardClicked = shoppingCardClicked)

        Spacer(modifier = Modifier.height(12.dp))

        when (categoryState) {
            is UIState.Error -> {
                Log.d("Categories", "Error")
            }

            is UIState.Loading -> {}

            is UIState.Success -> {
                val categories = (categoryState as UIState.Success<List<String>>).data
                Log.d("Categories", "$categories")
                CategoryProduct(
                    categories = listOf("All") + categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = {
                        viewmodel.onCategorySelected(it)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (productState) {
            is UIState.Success -> {
                val products = (productState as UIState.Success<List<Product>>).data
                ProductContent(products = products, productClicked)
            }

            is UIState.Error -> {}

            is UIState.Loading -> {}
        }
    }
}

@Composable
fun ProductContent(products: List<Product>, productClicked: (Int) -> Unit) {

    // ürün listesi
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, onClick = {
                //TODO : DETAYA GEÇİŞ
                productClicked(product.id)
                Log.d("id", "${product.id}")

            })
        }
    }

}


@Composable
fun ShoppingBar(
    shoppingCardClicked : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Catalog",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            color = Color(0xFF222222),
            modifier = Modifier
        )

        Card(
            modifier = Modifier.size(45.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(Color.White),
            // TODO : Sepet sayfasına gidiş
            onClick = {shoppingCardClicked()}
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons_bag),
                contentDescription = "shopping cart",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            )
        }
    }
}


@Composable
fun CategoryProduct(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(categories) { category ->
            CategoryCard(category = category,
                isSelected = category.equals(selectedCategory, ignoreCase = true),
                onClick = { onCategorySelected(category) })
        }
    }
}


