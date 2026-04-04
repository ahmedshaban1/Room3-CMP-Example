package org.room3.exmple.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.room3.exmple.data.mapper.toDomain
import org.room3.exmple.data.remote.ProductApi
import org.room3.exmple.domain.model.Product
import org.room3.exmple.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> = flow {
        val response = api.getProducts()
        emit(response.products.map { it.toDomain() })
    }

    override fun getProduct(id: Int): Flow<Product> = flow {
        val dto = api.getProduct(id)
        emit(dto.toDomain())
    }
}
