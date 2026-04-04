package org.room3.exmple

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import kotlinx.serialization.Serializable
import org.room3.exmple.data.remote.ProductApi
import org.room3.exmple.data.repository.ProductRepositoryImpl
import org.room3.exmple.domain.repository.ProductRepository
import org.room3.exmple.presentation.detail.ProductDetailViewModel
import org.room3.exmple.presentation.list.ProductListViewModel

@Serializable
sealed interface Route : NavKey

@Serializable
data object ProductListRoute : Route

@Serializable
data class ProductDetailRoute(val productId: Int) : Route

@Composable
@Preview
fun App() {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .components {
                    add(KtorNetworkFetcherFactory())
                }
                .build()
        }

        val api = remember { ProductApi() }
        val repository: ProductRepository = remember { ProductRepositoryImpl(api) }
        val backStack = remember { mutableStateListOf<NavKey>(ProductListRoute) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ProductListRoute> {
                    val viewModel = viewModel { ProductListViewModel(repository) }
                    val state by viewModel.state.collectAsState()
                    ProductListScreen(
                        state = state,
                        onProductClick = { productId ->
                            backStack.add(ProductDetailRoute(productId))
                        },
                        onRetry = { viewModel.loadProducts() }
                    )
                }
                entry<ProductDetailRoute> { key ->
                    val viewModel = viewModel(key = "detail_${key.productId}") {
                        ProductDetailViewModel(key.productId, repository)
                    }
                    val state by viewModel.state.collectAsState()
                    ProductDetailScreen(
                        state = state,
                        onBack = { backStack.removeLastOrNull() },
                        onRetry = { viewModel.loadProduct() }
                    )
                }
            }
        )
    }
}
