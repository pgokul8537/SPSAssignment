package com.example.spsassignment.repository

import com.example.spsassignment.network.DataHandler
import com.example.spsassignment.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchData(query: String): DataHandler<SearchResponse>
}