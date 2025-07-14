package com.esrabildik.feature.homescreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrabildik.domain.model.Product
import com.esrabildik.domain.usecase.GetCategoryUsecase
import com.esrabildik.domain.usecase.GetProductByIdUseCase
import com.esrabildik.domain.usecase.GetProductCategoryName
import com.esrabildik.domain.usecase.GetProductUseCase
import com.esrabildik.domain.util.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoryUsecase: GetCategoryUsecase,
    private val getCategoryByIdUsecase: GetProductByIdUseCase,
    private val getCategoryByNameUsecase: GetProductCategoryName
) : ViewModel() {

    private var _uiState = MutableStateFlow<UIState<List<Product>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Product>>> = _uiState


    private val _categoryState = MutableStateFlow<UIState<List<String>>>(UIState.Loading)
    val categoryState: StateFlow<UIState<List<String>>> = _categoryState


    private val _productDetail = MutableStateFlow<APIResult<Product>>(APIResult.Loading)
    val productDetail: StateFlow<APIResult<Product>> = _productDetail


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory : StateFlow<String> = _selectedCategory

    init {
        loadProduct()
        loadCategories()
    }

    fun onCategorySelected(category: String) {
        Log.d("SelectedCategory", "Kategori: $category")
        _selectedCategory.value = category
        if (category == "All") {
            loadProduct()
        } else {
            val slugCategory = category.lowercase().replace(" ", "-")
            getProductByCategory(slugCategory)
        }
    }


    private fun loadProduct() {
        viewModelScope.launch {
            getProductUseCase().collect { result ->
                Log.d("RESULT", "$result")
                when (result) {
                    is APIResult.Loading -> {
                        Log.d("ApiResult", "Loading çalıştı")
                        _uiState.value = UIState.Loading
                    }

                    is APIResult.Error -> {
                        Log.d("ApiResult", "Error çalıştı")
                        _uiState.value = UIState.Error(result.message)
                    }

                    is APIResult.Success -> {
                        Log.d("ApiResult", "Success çalıştı")
                        _uiState.value = UIState.Success(result.data)
                    }
                }

            }
        }
    }

    fun fetchProductById(id: String) {
        viewModelScope.launch {
            getCategoryByIdUsecase(id).collect { result ->
                _productDetail.value = result
            }
        }
    }

    private fun getProductByCategory(category: String) {
        viewModelScope.launch {
            getCategoryByNameUsecase(category).collect { result ->
                _uiState.value = when (result) {
                    is APIResult.Success -> UIState.Success(result.data)
                    is APIResult.Error -> UIState.Error(result.message)
                    APIResult.Loading -> UIState.Loading
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoryUsecase().onStart {
                Log.d("getCategoryUsecase", "getcategoryusecase invokes")
                _uiState.value = UIState.Loading
            }.catch { throwable ->
                Log.d("getCategoryUsecase", "getcategoryusecase failed${throwable.message}")
                _categoryState.value = UIState.Error(throwable.message!!)
            }.collect { result ->
                when (result) {
                    is APIResult.Error -> {
                        Log.d("APIResult", "Error")
                        _categoryState.value = UIState.Error(result.message)
                    }

                    APIResult.Loading -> {}

                    is APIResult.Success -> {
                        _categoryState.value = UIState.Success(result.data)
                    }
                }
            }
        }
    }

    /*  private fun getCategory(product : List<Product>) : Flow<List<String>> {
        product.forEach {
            Log.d("Categories","${it.category}")
        }
        val productCategory = mutableSetOf<String>()
        product.forEach {
            productCategory.add(it.category)
        }
        Log.d("Categories","   ")
        productCategory.forEach{
            Log.d("ProductCategories","${it}")
        }
        return productCategory.toList()
    }
}*/

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

}

sealed interface UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>
    data object Loading : UIState<Nothing>
    data class Error(val error: String) : UIState<Nothing>
}
