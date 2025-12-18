package com.example.smartshop.data.remote

import com.example.smartshop.data.local.ProductEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {

    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    suspend fun syncProduct(product: ProductEntity) {
        productsRef.document(product.id.toString())
            .set(product)
            .await()
    }

    suspend fun deleteProduct(id: Int) {
        productsRef.document(id.toString()).delete().await()
    }

    fun listenProducts(onUpdate: (List<ProductEntity>) -> Unit) {
        productsRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val data = snapshot.toObjects(ProductEntity::class.java)
                onUpdate(data)
            }
        }
    }
}
