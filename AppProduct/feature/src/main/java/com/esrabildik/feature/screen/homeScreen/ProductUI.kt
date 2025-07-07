package com.esrabildik.feature.screen.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esrabildik.domain.model.Product
import com.esrabildik.feature.component.CategoryCard
import com.esrabildik.feature.component.ProductCard

import com.esrabildik.feature.viewmodel.ProductViewModel
import com.esrabildik.feature.viewmodel.UIState


@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewmodel: ProductViewModel = hiltViewModel()
) {
    //viewmodeldaki stateFlow'lar burada izlenmeye başlanır.
    //collectAsStateWithLifecycle() sayesinde,
    // -- composable aktifse veri dinlenir.
    // -- enkran değişirse otomatik durur.
    // her yeni state geldiğinde, composable yeniden çizilir. (recompose olur)

    val productState by viewmodel.uiState.collectAsStateWithLifecycle()
    val categoryState by viewmodel.categoryState.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        EmbeddedSearchBar()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(25.dp))
    ) {


        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            when (productState) {
                is UIState.Success -> {
                    val products = (productState as UIState.Success).data

                    when (categoryState) {
                        is UIState.Error -> {

                        }

                        UIState.Loading -> {

                        }

                        is UIState.Success -> {
                            val categories = (categoryState as UIState.Success<List<String>>).data
                            ProductContent(products, categories)
                        }
                    }

                }

                is UIState.Error -> {

                }

                is UIState.Loading -> {

                }
            }
        }
    }

}


@Composable
fun ProductContent(products: List<Product>, category: List<String>) {

    Column(modifier = Modifier.fillMaxSize()) {
        CategoryProduct(category)

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(2)
        ) {

            items(products) {
                ProductCard(product = it)
            }
        }
    }

}

@Composable
fun CategoryProduct(category: List<String>) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(category) { index ->
            CategoryCard(index)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
fun EmbeddedSearchBar() {


    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }


    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { newQuery ->
            print("performing search on query : $newQuery")
        },
        active = active,
        onActiveChange = { active = it }) {

    }
}


