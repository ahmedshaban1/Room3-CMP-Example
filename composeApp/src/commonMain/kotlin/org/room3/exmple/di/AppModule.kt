package org.room3.exmple.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.room3.exmple.data.remote.ProductApi
import org.room3.exmple.data.repository.ProductRepositoryImpl
import org.room3.exmple.domain.repository.ProductRepository
import org.room3.exmple.presentation.detail.ProductDetailViewModel
import org.room3.exmple.presentation.list.ProductListViewModel

val appModule = module {
    single { ProductApi() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    viewModel { ProductListViewModel(get()) }
    viewModel { (productId: Int) -> ProductDetailViewModel(productId, get()) }
}
