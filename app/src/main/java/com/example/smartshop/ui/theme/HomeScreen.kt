package com.example.smartshop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartshop.ui.home.ProductListScreen
import com.example.smartshop.ui.home.StatisticsScreen
import com.example.smartshop.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    productViewModel: ProductViewModel
) {
    val products = productViewModel.products.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "Gestion des Produits",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(20.dp))

        // Liste des produits
        ProductListScreen(productViewModel)

        Spacer(Modifier.height(20.dp))

        // Statistiques
        StatisticsScreen(productViewModel)
    }
}