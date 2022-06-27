package ru.daniilxt.feature.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.daniilxt.feature.database.dao.FavoriteCurrencyDao
import ru.daniilxt.feature.database.models.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class FavoriteCurrencyDB : RoomDatabase() {
    abstract fun favoriteCurrencyDao(): FavoriteCurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteCurrencyDB? = null

        fun getDatabase(context: Context): FavoriteCurrencyDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteCurrencyDB::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
