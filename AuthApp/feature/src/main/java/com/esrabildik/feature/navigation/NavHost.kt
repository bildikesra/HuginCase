package com.esrabildik.feature.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esrabildik.feature.login.LoginUI
import com.esrabildik.feature.register.RegisterUI

@Composable
fun AppNavHost(){
    Surface(color = MaterialTheme.colorScheme.background) {

        val navController = rememberNavController()

       NavHost(
           navController = navController,
           startDestination = NavGraph.LoginScreen.route
       ){
          composable(route = NavGraph.LoginScreen.route){
              LoginUI(navController = navController)
          }

           composable(route = NavGraph.RegisterScreen.route){
               RegisterUI(onSuccess = {}, navController = navController )
           }
       }

    }
}