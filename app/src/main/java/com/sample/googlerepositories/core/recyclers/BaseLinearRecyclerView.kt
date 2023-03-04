package com.sample.googlerepositories.core.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * A base RecyclerView adapter class that displays a list of items in a recycler view.
 */
abstract class BaseLinearRecyclerView<M, D : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    RecyclerView.Adapter<BaseLinearRecyclerView<M, D>.MyRecyclerViewViewHolder>() {

    private var itemsList: ArrayList<M> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MyRecyclerViewViewHolder =
        MyRecyclerViewViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: MyRecyclerViewViewHolder, position: Int) =
        holder.bind(itemsList[position], position)

    abstract fun onBindItem(binding: D, data: M, position: Int)

    override fun getItemCount(): Int = itemsList.size

    /**
     *  adds [item] into [itemsList] at specific position
     * @param position is the position to add to
     * @param item is the item to add to [itemsList]
     *
     * @return added ot not
     */
    private fun addItemAt(position: Int, item: M): Boolean {
        if (position < 0)
            return false

        if (position < itemsList.size) {
            itemsList.add(position, item)
            notifyItemInserted(position)
        } else {
            addItem(item)
        }

        return true
    }

    /**
     *  adds [item] into [itemsList] at last position
     * @param item is the item to add to [itemsList]
     *
     */
    fun addItem(item: M) {
        this.itemsList.add(item)
        this.notifyItemInserted(itemsList.size - 1)
    }

    /**
     *  gets item from [itemsList] at specific position
     * @param position is the position of item we want
     * @return M is the item at position [position]
     *
     */
    fun getItemAt(position: Int): M = itemsList[position]

    /**
     *  gets all items in [itemsList]
     * @return ArrayList<M> is the list of all items in [itemsList]
     *
     */
    fun getAllItems(): ArrayList<M> = itemsList

    /**
     *  remove item from [itemsList] at specific position and notify adapter item removed
     *  @param position is the position of item to remove
     * @return M is the item removed from [position]
     *
     */
    fun removeItemAt(position: Int): M? {
        if (position >= 0 && position < itemsList.size) {
            val removedItem = itemsList.removeAt(position)
            notifyItemRemoved(position)
            return removedItem
        }
        return null
    }

    /**
     *  moves item from [oldPosition] into [newPosition] and notify data set changed
     *  @param oldPosition is position of old item
     *  @param newPosition is the new position to move item to
     * @return boolean if moved or not
     *
     */
    fun moveItemFromTo(oldPosition: Int, newPosition: Int): Boolean {
        if (oldPosition >= 0 && newPosition >= 0 && oldPosition < itemsList.size && newPosition < itemsList.size) {
            val removedItem = itemsList.removeAt(oldPosition)
            addItemAt(newPosition, removedItem)
            return true
        }

        return false
    }

    /**
     * updates the recycler view list using diffCallback based on newList
     *
     * @param diffCallback is DiffUtil Callback
     * @param newList is the new List to show
     */
    fun updateListItems(diffCallback: DiffUtil.Callback, newList: List<M>) {
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.itemsList.clear()
        this.itemsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MyRecyclerViewViewHolder(val binding: D) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(row: M, position: Int) = onBindItem(binding, row, position)
    }
}

