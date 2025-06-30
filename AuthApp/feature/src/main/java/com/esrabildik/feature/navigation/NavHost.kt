package com.esrabildik.feature.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esrabildik.feature.login.LoginUI
import com.esrabildik.feature.register.RegisterUI

@Composable
fun AppNavHost(navController: NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {

        NavHost(
            navController = navController,
            startDestination = NavGraph.LoginScreen.route
        ) {
            composable(route = NavGraph.LoginScreen.route) {
                LoginUI(
                    navigateToRegistry = { navController.navigate(NavGraph.RegisterScreen.route) },
                )
            }

            composable(route = NavGraph.RegisterScreen.route) {
                RegisterUI(
                    navigationToLogin = { navController.navigate(NavGraph.LoginScreen.route)},
                )
            }


        }

    }
}