package org.room3.exmple.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.room3.exmple.domain.model.Product
import org.room3.exmple.domain.repository.FavoriteRepository
import org.room3.exmple.domain.repository.ProductRepository

data class ProductListState(
    val products: List<Product> = emptyList(),
    val favoriteIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState())
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    init {
        loadProducts()
        observeFavorites()
    }

    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .onStart {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { products ->
                    _state.update { it.copy(isLoading = false, products = products) }
                }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteIds()
                .collect { ids ->
                    _state.update { it.copy(favoriteIds = ids.toSet()) }
                }
        }
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(productId)
        }
    }
}
