package ru.daniilxt.feature.shared_adapter.view_holder

import ru.daniilxt.common.base.BaseViewHolder
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.databinding.ItemCurrencyCardBinding
import ru.daniilxt.feature.domain.model.Currency

class CurrencyViewHolder(private val binding: ItemCurrencyCardBinding) :
    BaseViewHolder<Currency, ItemCurrencyCardBinding>(binding) {
    fun bind(item: Currency, onFavoriteItemClickListener: (Currency) -> Unit) {
        binding.tvCurrency.text = item.name
        binding.chipCurrencyValue.text = item.value.toString()
        binding.ibLike.isEnabled = item.isFavorite
        binding.ibLike.setDebounceClickListener {
            onFavoriteItemClickListener(item)
        }
    }

    override fun bind(item: Currency) {
    }
}
