package ru.daniilxt.feature.popular.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase

class PopularViewModel(
    private val router: FeatureRouter,
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : BaseViewModel() {
    private val _currencyList: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyList: StateFlow<List<Currency>> get() = _currencyList

    fun loadCurrencyInfo(currencyName: String) {
        viewModelScope.launch {
            when (val call = getCurrencyListUseCase.invoke(currencyName)) {
                is RequestResult.Success -> {
                    _currencyList.value = call.data
                }
                is RequestResult.Error -> {}
            }
        }
    }
}
