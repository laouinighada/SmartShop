package com.example.smartshop.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState().value
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 🔥 Navigation automatique après connexion réussie
    LaunchedEffect(state.success) {
        if (state.success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "SmartShop", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading
        ) {
            Text("Se connecter")
        }

        if (state.error != null) {
            Text(
                text = "Erreur : ${state.error}",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
