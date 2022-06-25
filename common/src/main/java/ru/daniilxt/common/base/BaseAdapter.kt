package ru.daniilxt.common.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : BaseModel, VH : BaseViewHolder<T, *>> :
    RecyclerView.Adapter<VH>() {

    protected val data: MutableList<T> = mutableListOf()
    protected val differ by lazy { AsyncListDiffer(this, BaseDiffUtilCallback<T>()) }

    override fun getItemId(position: Int): Long = differ.currentList[position].id

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(differ.currentList[position])

    open fun bindData(data: List<T>) {
        differ.submitList(data)
    }
}
