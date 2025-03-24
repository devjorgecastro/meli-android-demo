package com.example.meli.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.meli.home.HomeScreen
import com.example.meli.product_detail.ProductDetailScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Nav.Screen.Home.route
    ) {

        homeScreen(
            navController = navHostController
        )
        productDetailScreen(
            navController = navHostController
        )
    }
}

private fun NavGraphBuilder.homeScreen(
    navController: NavHostController
) {
    composable(
        route = Nav.Screen.Home.route
    ) {
        HomeScreen(
            onNavToDetail = {
                navController.navigate(Nav.Screen.ProductDetail.route)
            }
        )
    }
}

private fun NavGraphBuilder.productDetailScreen(
    navController: NavHostController
) {
    composable(
        route = Nav.Screen.ProductDetail.route
    ) {
        ProductDetailScreen(
            onNavigateToHomeScreen = {
                navController.navigate(Nav.Screen.Home.route) {
                    popUpTo(Nav.Screen.Home.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}
