package com.sample.googlerepositories.ui.repositorieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.googlerepositories.core.BaseVM
import com.sample.googlerepositories.core.result.RemoteError
import com.sample.googlerepositories.models.Repository
import com.sample.googlerepositories.services.RetrofitGenerator
import com.sample.googlerepositories.utils.Event
import com.sample.googlerepositories.utils.enums.APIErrorCodes
import com.sample.googlerepositories.utils.extensions.toArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * View model class for the repository list screen.
 * This class is responsible for providing data to the repository list UI.
 **/
class RepositoryListVM : BaseVM() {

    // to handle retrieve repositories success
    private var _getReposSuccess: MutableLiveData<Event<ArrayList<Repository>>> = MutableLiveData()
    var getReposSuccess: LiveData<Event<ArrayList<Repository>>> = _getReposSuccess

    // to handle retrieve repositories failure
    private var _getReposFailure: MutableLiveData<Event<RemoteError>> = MutableLiveData()
    var getReposFailure: LiveData<Event<RemoteError>> = _getReposFailure

    // holds repositories original list where no filter applied on it
    var repositoriesOriginalList: ArrayList<Repository> = arrayListOf()

    // holds repositories current list in view
    var repositoriesCurrentList: ArrayList<Repository>? = null

    /**
     * flag to know current page, from ite we know the second page to fetch
     */
    var currentPage = 1

    /**
     * Makes an API call to fetch a list of Google repositories from the server.
     *
     * @param page is the page of the list to get
     */
    fun getGoogleRepositories(page: Int) {

//        Handler(Looper.getMainLooper()).postDelayed(
//            {
//            _getReposSuccess.postValue(Event(arrayListOf(
//
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name4","name4","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name5","name5","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//                Repository(1,"name1","name1","",1, Owner(1,"name1")),
//
//            )))
//
//            },
//            5000
//        )

        val getRepositoriesInstance = RetrofitGenerator.getGoogleRepositoriesInstance()

        getRepositoriesInstance.getGoogleRepositories(page)
            .enqueue(object : Callback<ArrayList<Repository>?> {
                override fun onResponse(
                    call: Call<ArrayList<Repository>?>,
                    response: Response<ArrayList<Repository>?>,
                ) {
                    // on success post the result
                    if (response.isSuccessful && response.body() != null) {
                        _getReposSuccess.postValue(Event(response.body()!!))
                    } else if (response.code() in APIErrorCodes.BadRequest.code) {
                        _getReposFailure.postValue(Event(RemoteError.BadRequest(response.message())))
                    } else if (response.code() in APIErrorCodes.ServerError.code) {
                        _getReposFailure.postValue(Event(RemoteError.ServerError(response.message())))
                    } else {
                        _getReposFailure.postValue(Event(RemoteError.GenericError()))
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repository>?>, t: Throwable) {
                    call.cancel()
                    _getReposFailure.postValue(Event(RemoteError.GenericError()))
                }
            })
    }


    /**
     * used to filter [list] based on name of repository using the [query] for filtering
     *
     * @param query is the query to filter using it the name of repository
     * @param list is the list to filter
     *
     * @return list of the repositories filtered based on [query]
     */
    fun filterRepositories(query: String, list: ArrayList<Repository>): ArrayList<Repository> {
        return list.filter {
            it.fullName?.contains(query, true) ?: false ||
                    it.name?.contains(query, true) ?: false
        }.toArrayList()
    }

}