package ru.daniilxt.feature.data.source

import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.Currency

interface FeatureDataSource {
    suspend fun getCurrencyList(currencyName: String): RequestResult<List<Currency>>
}
