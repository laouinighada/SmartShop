package com.example.smartshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartshop.data.local.ProductEntity
import com.example.smartshop.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    val products = repo.products

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    init {
        repo.listenCloudSync()
    }

    fun add(name: String, quantity: Int, price: Double) {
        if (name.isBlank() || quantity < 0 || price <= 0) {
            _message.value = "Données invalides"
            return
        }

        viewModelScope.launch {
            repo.add(ProductEntity(name = name, quantity = quantity, price = price))
            _message.value = "Produit ajouté"
        }
    }

    fun update(product: ProductEntity) = viewModelScope.launch {
        repo.update(product)
    }

    fun delete(product: ProductEntity) = viewModelScope.launch {
        repo.delete(product)
    }
}
