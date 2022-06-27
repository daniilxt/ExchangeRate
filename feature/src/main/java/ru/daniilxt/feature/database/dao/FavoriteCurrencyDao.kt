package ru.daniilxt.feature.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.daniilxt.feature.database.models.CurrencyEntity

@Dao
interface FavoriteCurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currency: CurrencyEntity)

    @Query("SELECT * FROM FAVORITE_CURRENCY_TABLE FCT WHERE FCT.isFavourite = 1")
    suspend fun getFavoriteCurrencies(): List<CurrencyEntity>
}
