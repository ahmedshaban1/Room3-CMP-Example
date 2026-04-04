package org.room3.exmple

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

data object ProductListKey
data class ProductDetailKey(val productId: Int)

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = remember { mutableStateListOf<Any>(ProductListKey) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ProductListKey> { key ->
                    NavEntry(key) {
                        ProductListScreen(
                            products = sampleProducts,
                            onProductClick = { productId ->
                                backStack.add(ProductDetailKey(productId))
                            }
                        )
                    }
                }
                entry<ProductDetailKey> { key ->
                    NavEntry(key) {
                        val product = sampleProducts.first { it.id == key.productId }
                        ProductDetailScreen(
                            product = product,
                            onBack = { backStack.removeLastOrNull() }
                        )
                    }
                }
            }
        )
    }
}
