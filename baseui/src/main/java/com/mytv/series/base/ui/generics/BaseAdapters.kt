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
    PagedListAdapter<T, ViewHolder<*,*>>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*,*> {
        return getViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder<*,*>, position: Int) {
        (getItem(position)?.getContent() to holder as? ViewHolder<Any, Any>).biLet { content, binder ->
            binder.bind(content)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder<*,*>, position: Int, payloads: MutableList<Any>) {
        (payloads.firstOrNull() to holder as? ViewHolder<Any,Any>).biLet { changes, binder ->
            binder.updateBind(changes)
        } ?: super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getType() ?: -1
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*, *>
}

interface RecyclerItem<out R, out S> {
    fun getType(): Int
    fun getId(): Int
    fun getComparator(): Any?
    fun getContent(): R
    fun getDiffResolver(): S?
}

class RecyclerItemDiffCallback<T : RecyclerItem<*, *>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(firsteItem: T, secondItem: T): Boolean = firsteItem.getId() == secondItem.getId()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getComparator() == secondItem.getComparator()
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = newItem.getDiffResolver()
}

abstract class ViewHolder<T: Any, R :Any>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)

    abstract fun updateBind(item: R)
}