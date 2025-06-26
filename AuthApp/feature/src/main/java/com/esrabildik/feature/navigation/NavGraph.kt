package com.esrabildik.feature.navigation

sealed class NavGraph (val route : String){

    object LoginScreen : NavGraph("login_screen")
    object RegisterScreen : NavGraph("register_screen")
}