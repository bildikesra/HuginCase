package com.esrabildik.feature.navigation

sealed class NavGraph (val route : String,val title : String){

    object LoginScreen : NavGraph("login_screen","Login")
    object RegisterScreen : NavGraph("register_screen", "Register")
}