package org.room3.exmple.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.room3.exmple.data.local.dao.FavoriteDao
import org.room3.exmple.data.local.entity.FavoriteEntity
import org.room3.exmple.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override fun getFavoriteIds(): Flow<List<Int>> {
        return favoriteDao.getAllFavoriteIds()
    }

    override suspend fun toggleFavorite(productId: Int) {
        val currentFavorites = favoriteDao.getAllFavoriteIds().first()
        if (productId in currentFavorites) {
            favoriteDao.removeFavorite(productId)
        } else {
            favoriteDao.addFavorite(FavoriteEntity(productId))
        }
    }
}
