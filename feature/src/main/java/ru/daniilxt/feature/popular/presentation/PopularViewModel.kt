package ru.daniilxt.feature.popular.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.model.FilterType
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase
import ru.daniilxt.feature.utils.CurrencyFilteringStrategy

class PopularViewModel(
    private val router: FeatureRouter,
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : BaseViewModel() {
    private val _currencyList: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyList: StateFlow<List<Currency>> get() = _currencyList

    private var currentFilterType = FilterType.ALPHABETICALLY_ASC

    fun loadCurrencyInfo(currencyName: String) {
        viewModelScope.launch {
            when (val call = getCurrencyListUseCase.invoke(currencyName)) {
                is RequestResult.Success -> {
                    _currencyList.value =
                        CurrencyFilteringStrategy().invoke(call.data, currentFilterType)
                }
                is RequestResult.Error -> {}
            }
        }
    }

    fun filterBy(filterType: FilterType) {
        currentFilterType = filterType
        _currencyList.value = CurrencyFilteringStrategy().invoke(_currencyList.value, filterType)
    }
}
