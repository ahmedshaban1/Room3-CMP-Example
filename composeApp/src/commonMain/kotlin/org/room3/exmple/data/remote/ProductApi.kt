package org.room3.exmple.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.room3.exmple.data.remote.dto.ProductDto
import org.room3.exmple.data.remote.dto.ProductsResponse

class ProductApi {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun getProducts(): ProductsResponse {
        return client.get("https://dummyjson.com/products").body()
    }

    suspend fun getProduct(id: Int): ProductDto {
        return client.get("https://dummyjson.com/products/$id").body()
    }
}
