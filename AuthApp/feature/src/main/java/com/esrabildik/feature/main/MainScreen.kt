package com.esrabildik.feature.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import com.esrabildik.domain.model.UserResponse
import com.esrabildik.feature.navigation.AppNavHost


@Composable
fun Main(
    startService : (user :  UserResponse) -> Unit
) {

    val navController = rememberNavController()
    val destination = navController.currentDestination

    Scaffold(
        topBar = {
            CustomTopBar(
                modifier = Modifier.background(Color.DarkGray),
                title = destination?.route.toString(),
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                onIconClick = {}
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AppNavHost(navController, startService)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onIconClick: () -> Unit
) {

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title, fontSize = 20.sp

            )
        },
        navigationIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Navigation Icon",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onBackground)

    )
}


