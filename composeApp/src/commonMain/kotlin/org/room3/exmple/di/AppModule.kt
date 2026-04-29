package org.room3.exmple.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.room3.exmple.data.local.createAppDatabase
import org.room3.exmple.data.remote.ProductApi
import org.room3.exmple.data.repository.FavoriteRepositoryImpl
import org.room3.exmple.data.repository.ProductRepositoryImpl
import org.room3.exmple.domain.repository.FavoriteRepository
import org.room3.exmple.domain.repository.ProductRepository
import org.room3.exmple.presentation.detail.ProductDetailViewModel
import org.room3.exmple.presentation.list.ProductListViewModel

val appModule = module {
    single { createAppDatabase() }
    single { get<org.room3.exmple.data.local.AppDatabase>().favoriteDao() }
    single { ProductApi() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    viewModel { ProductListViewModel(get(), get()) }
    viewModel { (productId: Int) -> ProductDetailViewModel(productId, get()) }
}
