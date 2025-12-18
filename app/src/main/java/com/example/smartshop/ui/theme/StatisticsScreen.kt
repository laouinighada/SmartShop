package com.example.smartshop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartshop.viewmodel.ProductViewModel

@Composable
fun StatisticsScreen(viewModel: ProductViewModel) {
    val products = viewModel.products.collectAsState(initial = emptyList()).value

    val totalCount = products.sumOf { it.quantity }
    val totalValue = products.sumOf { it.quantity * it.price }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📊 Statistiques", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(12.dp))

            Text("Nombre total de produits : $totalCount")
            Text("Valeur totale du stock : %.2f DT".format(totalValue))
        }
    }
}