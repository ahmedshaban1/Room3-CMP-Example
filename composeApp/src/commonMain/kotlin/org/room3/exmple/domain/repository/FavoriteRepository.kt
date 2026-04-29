package org.room3.exmple.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteIds(): Flow<List<Int>>
    suspend fun toggleFavorite(productId: Int)
}
