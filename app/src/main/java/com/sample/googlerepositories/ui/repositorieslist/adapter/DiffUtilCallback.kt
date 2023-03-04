package com.sample.googlerepositories.ui.repositorieslist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sample.googlerepositories.models.Repository

/**
 *  class is used to compare two lists of Repository objects and detect which items have been added,
 *  removed, or changed between the old and new versions of the list.
 *
 *  @param oldList is old list
 *  @param newList is new List
 */
class DiffUtilCallback(oldList: List<Repository>, newList: List<Repository>) :
    DiffUtil.Callback() {

    private val oldList: List<Repository>
    private val newList: List<Repository>

    init {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id === newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepository: Repository = oldList[oldItemPosition]
        val newRepository: Repository = newList[newItemPosition]
        return oldRepository.fullName.equals(newRepository.fullName)
    }

}