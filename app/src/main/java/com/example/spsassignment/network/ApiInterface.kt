package com.example.spsassignment.network

import com.example.spsassignment.network.model.SearchResponse
import com.example.spsassignment.network.model.TvShowDetailsResponse
import com.example.spsassignment.network.model.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET
    suspend fun getTvShows(@Url url: String, @Query("page") pageNo: Int): TvShowsResponse

    @GET
    suspend fun getMultiSearchData(
        @Url url: String, @Query("page") pageNo: Int, @Query("query") query: String
    ): SearchResponse

    @GET
    suspend fun getTvShowDetails(@Url url: String): TvShowDetailsResponse
}