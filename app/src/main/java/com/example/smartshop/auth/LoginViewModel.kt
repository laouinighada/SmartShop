package com.example.smartshop.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    suspend fun login(email: String, password: String) {
        try {
            _state.value = LoginState(loading = true)

            // 🔥 Log avant la tentative
            android.util.Log.d("LoginViewModel", "Tentative de connexion pour: $email")

            auth.signInWithEmailAndPassword(email, password).await()

            android.util.Log.d("LoginViewModel", "Connexion réussie")
            _state.value = LoginState(success = true)

        } catch (e: Exception) {
            // 🔥 Logs détaillés de l'erreur
            android.util.Log.e("LoginViewModel", "Erreur de connexion", e)
            android.util.Log.e("LoginViewModel", "Type: ${e.javaClass.simpleName}")
            android.util.Log.e("LoginViewModel", "Message: ${e.message}")
            android.util.Log.e("LoginViewModel", "Cause: ${e.cause?.message}")

            // 🔥 Erreur spécifique Firebase
            if (e is com.google.firebase.FirebaseException) {
                android.util.Log.e("LoginViewModel", "Code Firebase: ${e.localizedMessage}")
            }

            _state.value = LoginState(
                error = "${e.javaClass.simpleName}: ${e.message}"
            )
        }
    }
}
