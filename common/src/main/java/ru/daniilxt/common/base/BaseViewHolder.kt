package ru.daniilxt.common.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : BaseModel, VB : ViewBinding>(private val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T)
}
