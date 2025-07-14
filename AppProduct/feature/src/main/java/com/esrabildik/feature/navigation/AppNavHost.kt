package com.esrabildik.feature.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.esrabildik.feature.detailscreen.ProductDetailScreen
import com.esrabildik.feature.favoritescreen.FavoriteScreen
import com.esrabildik.feature.homescreen.screen.homeScreen.ProductScreen
import com.esrabildik.feature.orderScreen.ShoppingCardScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenNavItem.Home.route,
    ) {
        composable(
            ScreenNavItem.Home.route
        ) {
            ProductScreen(
                productClicked = {productId ->
                    navController.navigate(ScreenNavItem.DetailProduct.createRoute(productId))
                },
                shoppingCardClicked = {
                    navController.navigate(ScreenNavItem.ShoppingCard.route)
                }
            )
        }

        composable(
            ScreenNavItem.Favorites.route
        ) {
            FavoriteScreen()
        }

        composable(
            ScreenNavItem.DetailProduct.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(productId)
        }

        composable(
            ScreenNavItem.ShoppingCard.route
        ){
            ShoppingCardScreen()
        }
    }
}