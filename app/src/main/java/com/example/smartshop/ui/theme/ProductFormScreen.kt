package com.example.smartshop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartshop.viewmodel.ProductViewModel

@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Ajouter un produit", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantité") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Prix (DT)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                val qty = quantity.toIntOrNull() ?: 0
                val prc = price.toDoubleOrNull() ?: 0.0

                if (name.isNotBlank() && qty > 0 && prc > 0) {
                    viewModel.add(name, qty, prc)
                    // Réinitialiser les champs
                    name = ""
                    quantity = ""
                    price = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter")
        }
    }
}