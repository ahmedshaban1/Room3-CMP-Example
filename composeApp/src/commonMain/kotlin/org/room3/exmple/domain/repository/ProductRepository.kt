package org.room3.exmple.domain.repository

import kotlinx.coroutines.flow.Flow
import org.room3.exmple.domain.model.Product

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProduct(id: Int): Flow<Product>
}
