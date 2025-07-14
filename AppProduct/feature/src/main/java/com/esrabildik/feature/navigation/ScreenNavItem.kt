package com.esrabildik.feature.navigation


sealed class ScreenNavItem(val route : String) {
    data object Home : ScreenNavItem("home")
    data object Favorites : ScreenNavItem("favorite")
    data object ShoppingCard : ScreenNavItem("shopping_card")
    data object DetailProduct : ScreenNavItem("productDetail/{productId}"){
        fun createRoute(productId: Int) = "productDetail/$productId"
    }
}