package com.mytv.series.base.ui.generics

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mytv.common.biLet

abstract class BasePagedListAdapter<T : RecyclerItem<*, *>> :
    PagedListAdapter<T, RecyclerView.ViewHolder>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (getItem(position)?.getContent() to holder as? Binder<Any, Any>).biLet { content, binder ->
            binder.bind(content)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        (payloads.firstOrNull() to holder as? Binder<Any, Any>).biLet { changes, binder ->
            binder.updateBind(changes)
        } ?: super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: -1
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
}

interface RecyclerItem<out R, out S> {
    fun getType(): Int
    fun getId(): Int
    fun getComparator(): Any?
    fun getContent(): R
    fun getDiffResolver(): S?
}

abstract class ViewFactory {
    abstract fun getView(parent: ViewGroup, viewType: Int): View
}

class RecyclerItemDiffCallback<T : RecyclerItem<*, *>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(firsteItem: T, secondItem: T): Boolean = firsteItem.getId() == secondItem.getId()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getComparator() == secondItem.getComparator()
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = newItem.getDiffResolver()
}

abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

interface Binder<in T, in R> {

    fun bind(item: T)

    fun updateBind(item: R)
}