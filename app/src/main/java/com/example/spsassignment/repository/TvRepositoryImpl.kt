package com.example.spsassignment.repository

import android.content.Context
import com.example.spsassignment.network.ApiInterface
import com.example.spsassignment.network.BaseRepository
import com.example.spsassignment.network.DataStoreManager
import com.example.spsassignment.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.internal.format
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @ApplicationContext context: Context, val dataStoreManager: DataStoreManager
) : TvRepository, BaseRepository(context) {

    override suspend fun getTrendingTvShows(pageNo: Int) =
        enqueue { apiInterface.getTvShows(Constants.URL_TRENDING_TV, pageNo = pageNo) }


    override suspend fun getSimilarTvShows(tvId: String) =
        enqueue {
            apiInterface.getTvShows(
                format(Constants.URL_SIMILAR_TV, tvId),
                pageNo = 1
            )

        }

    override suspend fun getTvShowDetails(tvId: String) =
        enqueue { apiInterface.getTvShowDetails(format(Constants.URL_TV_DETAILS, tvId)) }

    override suspend fun addToFavorite(tvId: String, favorite: Boolean) {

        val map: MutableMap<String, Boolean> = dataStoreManager.getFavorites()
        map[tvId] = favorite
        dataStoreManager.setFavoriteData(Gson().toJson(map))

    }

    override suspend fun getFavoriteList(): MutableMap<String, Boolean> =
        dataStoreManager.getFavorites()
}