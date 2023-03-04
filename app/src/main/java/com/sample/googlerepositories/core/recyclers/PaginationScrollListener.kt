package com.sample.googlerepositories.core.recyclers

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * listens for scrolling events and triggers a callback when the user has scrolled to the
 * end of the list, indicating that it's time to load more data.
 */
abstract class PaginationScrollListener(private var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= totalPageCount) {
                loadMoreItems()
            }
        }
    }

    /**
     * handles loading more items when finish from a page
     */
    protected abstract fun loadMoreItems()

    /**
     * get total pages count
     *
     * @return Int is count of pages
     */
    abstract val totalPageCount: Int

    /**
     * check if reached last page
     *
     * @return Boolean true or false for last page reached or not
     */
    abstract val isLastPage: Boolean

    /**
     * check if loading
     *
     * @return Boolean true or false for loading or not
     */
    abstract var isLoading: Boolean
}