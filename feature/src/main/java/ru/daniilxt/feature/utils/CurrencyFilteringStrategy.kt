package ru.daniilxt.feature.utils

import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.domain.model.FilterType

class CurrencyFilteringStrategy {
    operator fun invoke(data: List<Currency>, filterType: FilterType): List<Currency> =
        when (filterType) {
            FilterType.ALPHABETICALLY_ASC -> data.sortedBy { it.name }
            FilterType.ALPHABETICALLY_DESC -> data.sortedByDescending { it.name }
            FilterType.VALUE_ASC -> data.sortedBy { it.value }
            FilterType.VALUE_DESC -> data.sortedByDescending { it.value }
        }
}
