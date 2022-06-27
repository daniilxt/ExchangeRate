package ru.daniilxt.feature.interactors

import ru.daniilxt.feature.domain.model.FilterType

interface IUpdatable {
    fun update(currencyName: String)
    fun load(currencyName: String)
    fun filterBy(filterType: FilterType)
}
