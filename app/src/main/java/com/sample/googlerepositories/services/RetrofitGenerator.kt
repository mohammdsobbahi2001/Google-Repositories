package com.sample.googlerepositories.services

import com.sample.googlerepositories.services.googlerepos.GetGoogleRepositoriesAPI
import com.sample.googlerepositories.utils.constants.APILinks
import com.sample.googlerepositories.utils.constants.TimeConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * A class for generating Retrofit instances with a base URL.
 */
object RetrofitGenerator {

    private val client = OkHttpClient.Builder()
        .readTimeout(TimeConstants.API_TIME_OUT, TimeUnit.SECONDS)
        .build()

    private var getGoogleRepositoriesRetrofit = Retrofit.Builder()
        .baseUrl(APILinks.GET_GOOGLE_REPOSITORIES_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * creates an instance of getGoogleRepositoriesRetrofit
     *
     * @return is getGoogleRepositoriesRetrofit instance created
     */
    fun getGoogleRepositoriesInstance(): GetGoogleRepositoriesAPI =
        getGoogleRepositoriesRetrofit.create(GetGoogleRepositoriesAPI::class.java)

}