package ru.daniilxt.feature.domain.model

import ru.daniilxt.common.base.BaseModel
import java.time.LocalDate

data class CurrencyBundle(
    override val id: Long = 0,
    val date: LocalDate,
    val currencyList: List<Currency>
) : BaseModel(id) {
    override fun isContentEqual(other: BaseModel): Boolean {
        return other is CurrencyBundle && this.date == other.date &&
            this.currencyList == other.currencyList
    }
}
