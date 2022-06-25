package ru.daniilxt.feature.main_screen.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.common.model.ResponseState
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase

class MainScreenViewModel(
    private val router: FeatureRouter,
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : BaseViewModel() {

    private val _currencyTitles: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val currencyTitles: StateFlow<List<String>> get() = _currencyTitles

    init {
        getCurrency()
    }

    fun getCurrency() {
        viewModelScope.launch {
            setEventState(ResponseState.Initial)
            when (val call = getCurrencyListUseCase.invoke("USD")) {
                is RequestResult.Success -> {
                    _currencyTitles.value = call.data.map { it.name }
                    setEventState(ResponseState.Success)
                }
                is RequestResult.Error -> {
                    setEventState(ResponseState.Error(call.error))
                }
            }
        }
    }
}
