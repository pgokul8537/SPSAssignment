package com.example.spsassignment.repository

import android.content.Context
import com.example.spsassignment.network.ApiInterface
import com.example.spsassignment.network.BaseRepository
import com.example.spsassignment.utils.Constants.URL_SEARCH_TV
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @ApplicationContext context: Context
) : SearchRepository,
    BaseRepository(context) {

    override suspend fun getSearchData(query: String) = enqueue {
        apiInterface.getMultiSearchData(
            url = URL_SEARCH_TV,
            pageNo = 1,
            query = query
        )

    }
}