package ru.daniilxt.feature.domain.model

import ru.daniilxt.common.base.BaseModel

data class Currency(
    override val id: Long = 0,
    val name: String = "",
    val value: Double,
    val isFavorite: Boolean = false
) : BaseModel(id) {
    override fun isContentEqual(other: BaseModel): Boolean {
        return other is Currency && this.name == other.name &&
            this.value == other.value && this.isFavorite == other.isFavorite
    }
}
