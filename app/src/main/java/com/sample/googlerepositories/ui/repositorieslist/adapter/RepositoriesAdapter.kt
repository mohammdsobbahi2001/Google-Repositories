package com.sample.googlerepositories.ui.repositorieslist.adapter

import com.bumptech.glide.Glide
import com.sample.googlerepositories.R
import com.sample.googlerepositories.core.recyclers.BaseLinearRecyclerView
import com.sample.googlerepositories.databinding.ItemRepositoryBinding
import com.sample.googlerepositories.models.Repository
import com.sample.googlerepositories.utils.extensions.toGone
import com.sample.googlerepositories.utils.extensions.toVisible

/**
 * repository recycler view adapter class, here each item in list is init and set
 *
 * @param onRepositoryClick callback for handling click on repository item
 */
class RepositoriesAdapter(private val onRepositoryClick: (Repository) -> Unit) :
    BaseLinearRecyclerView<Repository, ItemRepositoryBinding>(R.layout.item_repository) {

    private val loadingItemId = null

    /**
     * flag to check whether there is a loading footer added or not
     */
    private var isLoadingAdded = false

    override fun onBindItem(
        binding: ItemRepositoryBinding,
        data: Repository,
        position: Int,
    ) {
        if (data.id == null) {  // case loading show progress

            binding.cardAvatar.toGone()
            binding.tvName.toGone()
            binding.tvCreationDate.toGone()
            binding.progressLoad.toVisible()

            binding.clRepositoryItem.setOnClickListener(null)

        } else {        // else bind and show data

            binding.cardAvatar.toVisible()
            binding.tvName.toVisible()
            binding.tvCreationDate.toVisible()
            binding.progressLoad.toGone()

            binding.repository = data
            binding.position = position

            Glide.with(binding.root.context).load(data.owner?.avatarUrl).into(binding.imgAvatar)

            binding.clRepositoryItem.setOnClickListener {
                onRepositoryClick(data)
            }
        }
    }

    /**
     * add a loading footer by adding item with id [loadingItemId], and change isLoadingAdded for true
     */
    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            addItem(Repository(loadingItemId))
        }
    }

    /**
     * removes loading footer, and remove the item from the list
     */
    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = itemCount - 1
        val item = getAllItems().lastOrNull()
        if (item != null) {
            getAllItems().remove(item)
            notifyItemRemoved(position)
        }
    }
}