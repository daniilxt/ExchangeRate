package ru.daniilxt.feature.database.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.daniilxt.feature.domain.model.Currency

@Entity(
    tableName = "favorite_currency_table",
    indices = [Index(value = ["currencyName"], unique = true)]
)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val currencyName: String,
    val isFavourite: Boolean
)

fun Currency.toCurrencyEntity() = CurrencyEntity(
    currencyName = name,
    isFavourite = isFavorite
)

fun CurrencyEntity.toCurrency() = Currency(
    name = currencyName,
    value = 0.0,
    isFavorite = isFavourite
)
