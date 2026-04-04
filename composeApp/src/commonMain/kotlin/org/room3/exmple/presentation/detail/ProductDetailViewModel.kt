package org.room3.exmple.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.room3.exmple.domain.model.Product
import org.room3.exmple.domain.repository.ProductRepository

data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductDetailViewModel(
    private val productId: Int,
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            repository.getProduct(productId)
                .onStart {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { product ->
                    _state.update { it.copy(isLoading = false, product = product) }
                }
        }
    }
}
