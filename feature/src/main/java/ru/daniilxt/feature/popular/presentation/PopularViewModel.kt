package ru.daniilxt.feature.popular.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.Currency

class PopularViewModel(private val router: FeatureRouter) : BaseViewModel() {
    private val _currencyList: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyList: StateFlow<List<Currency>> get() = _currencyList

    init {
        _currencyList.value = listOf(
            Currency(
                name = "RUB",
                value = 0.05,
                isFavorite = true
            ),
            Currency(
                name = "USD",
                value = 1.5,
                isFavorite = false
            ),
            Currency(
                name = "EUR",
                value = 1.0,
                isFavorite = false
            ),
            Currency(
                name = "UAH",
                value = 57.5,
                isFavorite = false
            ),
        )
    }
}
