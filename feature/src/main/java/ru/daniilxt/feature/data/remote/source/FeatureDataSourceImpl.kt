package ru.daniilxt.feature.data.remote.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.daniilxt.common.error.BaseError
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.data.remote.api.FeatureApiService
import ru.daniilxt.feature.data.source.FeatureDataSource
import ru.daniilxt.feature.domain.model.Currency
import timber.log.Timber
import javax.inject.Inject

class FeatureDataSourceImpl @Inject constructor(
    private val featureApiService: FeatureApiService
) : FeatureDataSource {
    override suspend fun getCurrencyList(currencyName: String): RequestResult<List<Currency>> =
        withContext(Dispatchers.IO) {
            try {
                val response = featureApiService.getCurrencyList(currencyName)
                val res = response.body()
                return@withContext if (response.isSuccessful && res != null) {
                    RequestResult.Success(res.currencyClass.getCurrencyList())
                } else {
                    RequestResult.Error(BaseError.Error)
                }
            } catch (e: Exception) {
                Timber.i("??\n\n\n $e")
                return@withContext RequestResult.Error(BaseError.Error)
            }
        }
}
