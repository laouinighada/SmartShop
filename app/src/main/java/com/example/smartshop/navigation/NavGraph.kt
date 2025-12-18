package com.example.smartshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartshop.auth.LoginScreen
import com.example.smartshop.ui.home.HomeScreen
import com.example.smartshop.viewmodel.ProductViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(productViewModel = productViewModel)
        }
    }
}