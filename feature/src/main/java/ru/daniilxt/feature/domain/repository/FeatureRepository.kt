package ru.daniilxt.feature.domain.repository

import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.Currency

interface FeatureRepository {
    suspend fun getCurrencyList(currencyName: String): RequestResult<List<Currency>>
}
