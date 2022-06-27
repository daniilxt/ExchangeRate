package ru.daniilxt.feature.shared_view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseViewModel
import ru.daniilxt.common.error.RequestResult
import ru.daniilxt.feature.database.models.toCurrency
import ru.daniilxt.feature.database.models.toCurrencyEntity
import ru.daniilxt.feature.database.repository.FavoriteCurrencyRepository
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.model.FilterType
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase
import ru.daniilxt.feature.utils.CurrencyFilteringStrategy

class SharedCurrencyViewModel(
    private val repository: FavoriteCurrencyRepository,
    private val getCurrencyListUseCase: GetCurrencyListUseCase
) : BaseViewModel() {
    // Main currency flow
    private val _currencyList: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyList: StateFlow<List<Currency>> get() = _currencyList

    // This data flow return favorite user currency from database
    private val _favoriteCurrenciesDB: MutableStateFlow<List<Currency>> =
        MutableStateFlow(emptyList())

    // This data flow is obtained as a result of filtering the main currency flow
    private val _favoriteCurrencies: MutableStateFlow<List<Currency>> =
        MutableStateFlow(emptyList())
    val favoriteCurrencies: StateFlow<List<Currency>> get() = _favoriteCurrencies

    private var currentFilterType = FilterType.ALPHABETICALLY_ASC

    init {
        viewModelScope.launch {
            _favoriteCurrenciesDB.value = getFavoriteCurrencies()
        }
    }

    // Updates the current currency values from the server applying filtering strategy and set user favorite currency
    fun updateCurrencyInfo(currencyName: String) {
        viewModelScope.launch {
            when (val call = getCurrencyListUseCase.invoke(currencyName)) {
                is RequestResult.Success -> {
                    val data = call.data.map { item ->
                        if (item.name in _favoriteCurrenciesDB.value.map { it.name }) {
                            item.copy(
                                isFavorite = true
                            )
                        } else {
                            item
                        }
                    }
                    _currencyList.value =
                        CurrencyFilteringStrategy().invoke(data, currentFilterType)
                    loadFavoriteCurrencyInfo()
                }
                is RequestResult.Error -> {}
            }
        }
    }

    fun loadFavoriteCurrencyInfo() {
        _favoriteCurrencies.value = _currencyList.value
            .filter { item -> item.name in _favoriteCurrenciesDB.value.map { it.name } }
    }

    fun filterBy(filterType: FilterType) {
        currentFilterType = filterType
        _currencyList.value = CurrencyFilteringStrategy().invoke(_currencyList.value, filterType)
        loadFavoriteCurrencyInfo()
    }

    // Adds or removes currency from favorites
    fun changeFavoriteState(currency: Currency) {
        viewModelScope.launch {
            val newState = currency.copy(isFavorite = !currency.isFavorite)
            repository.addCurrency(newState.toCurrencyEntity())
            _currencyList.value = _currencyList.value.map {
                if (it == currency) newState else it
            }
            updateCurrencyInFavorite(currency)
            loadFavoriteCurrencyInfo()
        }
    }

    // Updates local favorite flow
    private fun updateCurrencyInFavorite(currency: Currency) {
        if (currency.name in _favoriteCurrenciesDB.value.map { it.name }) {
            _favoriteCurrenciesDB.value =
                _favoriteCurrenciesDB.value.filter { it.name != currency.name }
        } else {
            _favoriteCurrenciesDB.value = _favoriteCurrenciesDB.value + listOf(currency)
        }
    }

    private suspend fun getFavoriteCurrencies(): List<Currency> {
        return repository.getFavoriteCurrencies().map { it.toCurrency() }
    }

    companion object {
        private var INSTANCE: SharedCurrencyViewModel? = null
        fun newInstance(
            repository: FavoriteCurrencyRepository,
            getCurrencyListUseCase: GetCurrencyListUseCase
        ): SharedCurrencyViewModel {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = SharedCurrencyViewModel(
                    repository,
                    getCurrencyListUseCase
                )
                INSTANCE = instance
                return instance
            }
        }
    }
}
