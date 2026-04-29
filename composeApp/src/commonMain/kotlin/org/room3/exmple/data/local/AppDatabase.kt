package org.room3.exmple.data.local

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import org.room3.exmple.data.local.dao.FavoriteDao
import org.room3.exmple.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
