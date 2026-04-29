package org.room3.exmple.data.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbPath = "${NSHomeDirectory()}/Documents/favorites.db"
    return Room.databaseBuilder<AppDatabase>(name = dbPath)
}
