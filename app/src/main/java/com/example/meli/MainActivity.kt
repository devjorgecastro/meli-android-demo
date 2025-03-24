package com.example.meli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.meli.navigation.AppNavHost
import com.example.meli.ui.theme.MeliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeliTheme(dynamicColor = false) {
                AppNavHost(
                    navHostController = rememberNavController(),
                )
            }
        }
    }
}
