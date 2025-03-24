package com.example.meli.navigation

object Nav {
    internal sealed class Screen(val route: String) {
        data object Home : Screen("home")
        data object ProductDetail : Screen("product-detail")
    }
}