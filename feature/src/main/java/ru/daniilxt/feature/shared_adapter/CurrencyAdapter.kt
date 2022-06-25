package ru.daniilxt.feature.shared_adapter

import android.view.ViewGroup
import ru.daniilxt.common.base.BaseAdapter
import ru.daniilxt.common.extensions.viewBinding
import ru.daniilxt.feature.databinding.ItemCurrencyCardBinding
import ru.daniilxt.feature.domain.model.Currency
import ru.daniilxt.feature.shared_adapter.view_holder.CurrencyViewHolder

class CurrencyAdapter(private val onFavoriteItemClickListener: (Currency) -> Unit) :
    BaseAdapter<Currency, CurrencyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(parent.viewBinding(ItemCurrencyCardBinding::inflate))

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(differ.currentList[position], this.onFavoriteItemClickListener)
    }
}
