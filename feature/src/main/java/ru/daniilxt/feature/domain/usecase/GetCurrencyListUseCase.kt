package ru.daniilxt.feature.domain.usecase

import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.repository.FeatureRepository
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    suspend operator fun invoke(currencyName: String): RequestResult<List<Currency>> {
        return featureRepository.getCurrencyList(currencyName)
    }
}
