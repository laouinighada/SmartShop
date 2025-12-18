package com.example.smartshop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartshop.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel
) {
    val products = viewModel.products.collectAsState(initial = emptyList()).value

    Column {
        // 🔥 Formulaire d'ajout EN PREMIER
        ProductFormScreen(viewModel)

        Spacer(Modifier.height(16.dp))

        Text("Liste des produits", style = MaterialTheme.typography.titleLarge)

        // 🔥 Liste des produits
        products.forEach { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(product.name, style = MaterialTheme.typography.titleMedium)
                    Text("Quantité : ${product.quantity}")
                    Text("Prix : ${product.price} DT")

                    Button(
                        onClick = { viewModel.delete(product) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Supprimer")
                    }
                }
            }
        }
    }
}