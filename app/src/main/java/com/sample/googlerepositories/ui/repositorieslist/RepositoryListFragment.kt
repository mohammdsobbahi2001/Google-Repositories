package com.sample.googlerepositories.ui.repositorieslist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.googlerepositories.R
import com.sample.googlerepositories.core.BaseFragment
import com.sample.googlerepositories.core.recyclers.PaginationScrollListener
import com.sample.googlerepositories.databinding.FragmentRepositoriesListBinding
import com.sample.googlerepositories.models.Repository
import com.sample.googlerepositories.ui.repositorieslist.adapter.DiffUtilCallback
import com.sample.googlerepositories.ui.repositorieslist.adapter.RepositoriesAdapter
import com.sample.googlerepositories.utils.constants.TimeConstants
import com.sample.googlerepositories.utils.extensions.toArrayList
import com.sample.googlerepositories.utils.extensions.toGone
import com.sample.googlerepositories.utils.extensions.toVisible

/**
 * Fragment class for the repository list screen.
 */
class RepositoryListFragment :
    BaseFragment<FragmentRepositoriesListBinding, RepositoryListVM>(R.layout.fragment_repositories_list) {

    override val viewModel: RepositoryListVM by viewModels()

    /**
     * repositories recycler view adapter
     */
    private lateinit var repositoriesAdapter: RepositoriesAdapter

    /**
     * flag for check if loading new page
     */
    private var isLoading = false

    /**
     * flag for check if last page reached
     */
    private var isLastPage = false

    override fun onFragmentCreated(savedInstanceState: Bundle?) {

        // handle back press by moving activity back to stack
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().moveTaskToBack(true)
        }

        // set up repository adapter for recycler view
        setupRepositoriesAdapter()

        // start progress by shimmer
        showProgressShimmerEffect()

        // if no result for repositories, then call the API
        if (viewModel.repositoriesCurrentList.isNullOrEmpty()) {
            // api call to get result
            getGoogleRepositories()
        } else {

            // set the result in recycler view using DiffUtilCallback
            val oldList = repositoriesAdapter.getAllItems()
            val newList = viewModel.repositoriesCurrentList!!.map { it.copy() }.toArrayList()
            val diffUtilCallback = DiffUtilCallback(oldList, newList)
            repositoriesAdapter.updateListItems(
                diffUtilCallback,
                newList
            )
            viewModel.repositoriesCurrentList = repositoriesAdapter.getAllItems()

            // stop load effect
            stopProgressShimmerEffect(repositoriesAdapter.itemCount)
        }

        // handle search view query
        binding.searchViewRepos.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    handleSearch(query, viewModel.repositoriesOriginalList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {

                        if (this@RepositoryListFragment::repositoriesAdapter.isInitialized) {

                            // if empty search, then show repositoriesOriginalList
                            val oldList = repositoriesAdapter.getAllItems()
                            val diffUtilCallback =
                                DiffUtilCallback(oldList, viewModel.repositoriesOriginalList)

                            repositoriesAdapter.updateListItems(
                                diffUtilCallback,
                                viewModel.repositoriesOriginalList
                            )

                            viewModel.repositoriesCurrentList = repositoriesAdapter.getAllItems()
                        }
                    } else {
                        handleSearch(newText, viewModel.repositoriesOriginalList)
                    }
                }
                return true
            }
        })

    }

    /**
     * used to show shimmer effect UI
     */
    private fun showProgressShimmerEffect() {
        binding.rvGoogleRepository.toGone()
        binding.tvNoRepository.toGone()
        binding.shimmerLayout.toVisible()
        binding.shimmerLayout.startShimmer()
    }

    /**
     * used to stop shimmer effect UI, and handle UI based on the count of results
     *
     * @param resultCount is the count of results
     */
    private fun stopProgressShimmerEffect(resultCount: Int) {
        if (resultCount <= 0) {
            binding.tvNoRepository.toVisible()
            binding.rvGoogleRepository.toGone()
        } else {
            binding.tvNoRepository.toGone()
            binding.rvGoogleRepository.toVisible()
        }

        binding.shimmerLayout.toGone()
        binding.shimmerLayout.stopShimmer()
    }

    /**
     * performs API call to get google repositories from server, handle success and failure
     */
    private fun getGoogleRepositories() {

        // perform call
        viewModel.getGoogleRepositories(1)

        // handle success
        viewModel.getReposSuccess.observe(this) { response ->
            if (response.getContentIfNotHandled() != null) {
                val repositories = response.peekContent()

                val oldList = repositoriesAdapter.getAllItems()

                if (viewModel.currentPage > 1) {        // to check if the result is from new page
                    repositoriesAdapter.removeLoadingFooter()

                    // add repositories to old list
                    viewModel.repositoriesCurrentList = arrayListOf<Repository>().apply {
                        addAll(oldList)
                        addAll(repositories)
                    }

                    viewModel.repositoriesOriginalList =
                        viewModel.repositoriesCurrentList!!.map { it.copy() }.toArrayList()

                } else {
                    viewModel.repositoriesOriginalList =
                        repositories.map { it.copy() }.toArrayList()
                    viewModel.repositoriesCurrentList = arrayListOf<Repository>().apply {
                        addAll(repositories)
                    }

                    // when empty repository retrieved from pages, then last page reached
                    if (repositories.isEmpty()) {
                        isLastPage = true
                    }
                }

                // show the results
                val diffUtilCallback =
                    DiffUtilCallback(oldList, viewModel.repositoriesCurrentList!!)

                repositoriesAdapter.updateListItems(
                    diffUtilCallback,
                    viewModel.repositoriesCurrentList!!
                )
                viewModel.repositoriesCurrentList = repositoriesAdapter.getAllItems()

                // stop load effect
                stopProgressShimmerEffect(repositoriesAdapter.itemCount)

                isLoading = false
            }
        }

        // handle failure
        viewModel.getReposFailure.observe(this) {
            if (it.getContentIfNotHandled() != null) {
                stopProgressShimmerEffect(-1)
                isLoading = false
            }
        }
    }

    /**
     * handles the search in repertoires list after submitted
     *
     * @param query is the search text
     * @param list is list of repositories
     */
    private fun handleSearch(query: String, list: ArrayList<Repository>) {
        val listFilter = viewModel.filterRepositories(query, list)

        if (this@RepositoryListFragment::repositoriesAdapter.isInitialized) {

            val oldList = repositoriesAdapter.getAllItems()
            repositoriesAdapter.updateListItems(DiffUtilCallback(oldList, listFilter), listFilter)
            viewModel.repositoriesCurrentList = repositoriesAdapter.getAllItems()
        }
    }

    /**
     * sets up [repositoriesAdapter] and set it as adapter for recycler view, also handle click on item items
     */
    private fun setupRepositoriesAdapter() {

        repositoriesAdapter = RepositoriesAdapter { repository ->
            // handle click on repository by showing its details
            navigateTo(
                RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(
                    repository
                )
            )
        }

        binding.rvGoogleRepository.adapter = repositoriesAdapter

        // handle scroll down by registering [PaginationScrollListener]
        binding.rvGoogleRepository.addOnScrollListener(object :
            PaginationScrollListener(binding.rvGoogleRepository.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                this@RepositoryListFragment.isLoading = true

                // delay for API call
                Handler(Looper.getMainLooper()).postDelayed({

                    // load next pages
                    viewModel.currentPage += 1
                    viewModel.getGoogleRepositories(viewModel.currentPage)
                    repositoriesAdapter.addLoadingFooter()

                }, TimeConstants.API_DELAY)
            }

            override val totalPageCount: Int = 30
            override val isLastPage: Boolean = this@RepositoryListFragment.isLastPage
            override var isLoading: Boolean = this@RepositoryListFragment.isLoading
        })
    }

}