package com.example.smartshop.data.repository

import com.example.smartshop.data.local.ProductDao
import com.example.smartshop.data.local.ProductEntity
import com.example.smartshop.data.remote.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductRepository(
    private val dao: ProductDao,
    private val firestore: FirestoreService
) {

    // Flow pour observer les produits localement
    val products: Flow<List<ProductEntity>> = dao.getProducts()

    // Fonction suspend pour ajouter un produit local + cloud
    suspend fun add(product: ProductEntity) {
        dao.insert(product)          // Appel suspend depuis suspend
        firestore.syncProduct(product)
    }

    // Fonction suspend pour update
    suspend fun update(product: ProductEntity) {
        dao.update(product)
        firestore.syncProduct(product)
    }

    // Fonction suspend pour delete
    suspend fun delete(product: ProductEntity) {
        dao.delete(product)
        firestore.deleteProduct(product.id)
    }

    // Fonction pour écouter les changements cloud et les insérer localement
    fun listenCloudSync() {
        firestore.listenProducts { cloudProducts ->
            // Ici on doit lancer une coroutine pour appeler dao.insert
            CoroutineScope(Dispatchers.IO).launch {
                cloudProducts.forEach {
                    dao.insert(it)
                }
            }
        }
    }
}
