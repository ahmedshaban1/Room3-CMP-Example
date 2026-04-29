package org.room3.exmple

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.room3.exmple.di.appModule
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
    KoinApplication(application = { modules(appModule) }) {
        MaterialTheme {
            setSingletonImageLoaderFactory { context ->
                ImageLoader.Builder(context)
                    .components {
                        add(KtorNetworkFetcherFactory())
                    }
                    .build()
            }

            val backStack = remember { mutableStateListOf<NavKey>(ProductListRoute) }

            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<ProductListRoute> {
                        val viewModel = koinViewModel<ProductListViewModel>()
                        val state by viewModel.state.collectAsState()
                        ProductListScreen(
                            state = state,
                            onProductClick = { productId ->
                                backStack.add(ProductDetailRoute(productId))
                            },
                            onToggleFavorite = { productId ->
                                viewModel.toggleFavorite(productId)
                            },
                            onRetry = { viewModel.loadProducts() }
                        )
                    }
                    entry<ProductDetailRoute> { key ->
                        val viewModel = koinViewModel<ProductDetailViewModel>(
                            key = "detail_${key.productId}",
                            parameters = { parametersOf(key.productId) }
                        )
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
}
