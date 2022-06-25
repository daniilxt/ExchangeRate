package ru.daniilxt.feature.main_screen.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.feature.FeatureRouter

class MainScreenViewModel(
    private val router: FeatureRouter
) : BaseViewModel() {
    var currentSelectedPage = INITIAL_PAGE
        private set

    private val _currencyTitles: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val currencyTitles: StateFlow<List<String>> get() = _currencyTitles

    init {
        _currencyTitles.value = listOf("EUR", "RUB")
    }
    companion object {
        private const val INITIAL_PAGE = 0
    }
}
