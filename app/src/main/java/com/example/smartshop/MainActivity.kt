package com.example.smartshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.smartshop.data.local.AppDatabase
import com.example.smartshop.data.remote.FirestoreService
import com.example.smartshop.data.repository.ProductRepository
import com.example.smartshop.navigation.NavGraph
import com.example.smartshop.ui.theme.SmartShopTheme
import com.example.smartshop.viewmodel.ProductViewModel
import com.example.smartshop.viewmodel.ProductViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation Room + Repository
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "smartshop-db"
        ).build()

        val repository = ProductRepository(
            dao = db.productDao(),
            firestore = FirestoreService()
        )

        // 🔥 Créer le ViewModel avec Factory
        val factory = ProductViewModelFactory(repository)
        val productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        setContent {
            SmartShopTheme {
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    productViewModel = productViewModel
                )
            }
        }
    }
}