package ru.daniilxt.feature.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class FeatureRepositoryImpl @Inject constructor(private val featureDataSource: FeatureDataSource) :
    FeatureRepository {
    override suspend fun getCurrencyList(currencyName: String): RequestResult<List<Currency>> =
        withContext(Dispatchers.IO) {
            return@withContext featureDataSource.getCurrencyList(currencyName)
        }
}
