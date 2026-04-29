package org.room3.exmple.data.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

private lateinit var appContext: Context

fun initDatabaseContext(context: Context) {
    appContext = context.applicationContext
}


actual fun createAppDatabase(): AppDatabase {
    val dbFile = appContext.getDatabasePath("favorites.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).build()
}