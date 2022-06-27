package ru.daniilxt.common.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtilCallback<T : BaseModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ) = oldItem.isContentEqual(newItem)
}
