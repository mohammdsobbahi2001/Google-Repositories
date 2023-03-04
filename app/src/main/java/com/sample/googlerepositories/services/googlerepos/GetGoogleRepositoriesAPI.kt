package com.sample.googlerepositories.services.googlerepos

import com.sample.googlerepositories.models.Repository
import com.sample.googlerepositories.utils.constants.APILinks.GET_GOOGLE_REPOSITORIES_PATH
import com.sample.googlerepositories.utils.constants.ParameterKeys
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for making API calls to fetch Google repositories.
 */
interface GetGoogleRepositoriesAPI {

    /**
     * Makes an API GET call to fetch a list of Google repositories from the server.
     *
     * @return Call<ArrayList<Repository>> is the response of get request as list of Repository
     */
    @GET(GET_GOOGLE_REPOSITORIES_PATH)  //declare that final URL is the same as your base URL
    fun getGoogleRepositories(@Query(ParameterKeys.page) page: Int): Call<ArrayList<Repository>>

}