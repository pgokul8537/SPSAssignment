package com.example.spsassignment.repository

import com.example.spsassignment.network.DataHandler
import com.example.spsassignment.network.model.TvShowDetailsResponse
import com.example.spsassignment.network.model.TvShowsResponse
import kotlinx.coroutines.flow.Flow

interface TvRepository {

    suspend fun getTrendingTvShows(pageNo: Int): DataHandler<TvShowsResponse>
    suspend fun getSimilarTvShows(tvId: String): DataHandler<TvShowsResponse>
    suspend fun getTvShowDetails(tvId: String): DataHandler<TvShowDetailsResponse>
    suspend fun addToFavorite(tvId: String, favorite: Boolean)
    suspend fun getFavoriteList(): MutableMap<String, Boolean>
}