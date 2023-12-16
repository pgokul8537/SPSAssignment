package com.example.spsassignment.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spsassignment.network.DataHandler
import com.example.spsassignment.network.model.SearchResponse
import com.example.spsassignment.network.model.TvShowsResponse
import com.example.spsassignment.repository.SearchRepository
import com.example.spsassignment.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    val repository: TvRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _tvShowUiState = MutableStateFlow(TvShowUiState())
    val tvShowUiState = _tvShowUiState.asStateFlow()

    fun onSearchTextChange(text: String) {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                searchText = text
            )
        }
    }

    fun getSearchData() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                isSearching = true
            )
        }
        viewModelScope.launch {
            getSearchResponse(searchRepository.getSearchData(tvShowUiState.value.searchText)) { searchItems, errorMessage ->
                _tvShowUiState.update { currentState ->
                    currentState.copy(
                        failureMessage = errorMessage,
                        searchList = updateFavoriteValuesForSearch(searchItems),
                        isSearching = false
                    )
                }
            }
        }
    }

    fun clearText() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                searchList = emptyList(),
                isSearching = false, searchText = ""
            )
        }
    }

    fun getTrendingTvShows() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                isTrendingTvShowApiLoading = true
            )
        }
        viewModelScope.launch {
            getTvResponse(repository.getTrendingTvShows(1)) { tvShowItems, errorMessage ->
                _tvShowUiState.update { currentState ->
                    currentState.copy(
                        failureMessage = errorMessage,
                        trendingTvShows = updateFavoriteValues(tvShowItems),
                        isTrendingTvShowApiLoading = false
                    )
                }
            }

        }
    }

    private fun getTvResponse(
        responseResult: DataHandler<TvShowsResponse>,
        onResult: (List<TvShowsResponse.TvShowItem>, String) -> Unit,

        ) {
        when (responseResult) {
            is DataHandler.Failure -> {
                onResult(emptyList(), responseResult.message)
            }

            is DataHandler.NoInternetConnectionFailure -> {
                onResult(emptyList(), responseResult.message)
            }

            is DataHandler.Success -> {
                onResult(
                    if (responseResult.data.results.isNullOrEmpty()) emptyList() else responseResult.data.results,
                    ""
                )
            }
        }
    }

    private fun getSearchResponse(
        responseResult: DataHandler<SearchResponse>,
        onResult: (List<SearchResponse.SearchItem>, String) -> Unit,

        ) {
        when (responseResult) {
            is DataHandler.Failure -> {
                onResult(emptyList(), responseResult.message)
            }

            is DataHandler.NoInternetConnectionFailure -> {
                onResult(emptyList(), responseResult.message)
            }

            is DataHandler.Success -> {
                onResult(
                    if (responseResult.data.results.isNullOrEmpty()) emptyList() else responseResult.data.results,
                    ""
                )
            }
        }
    }

    fun addToFavorite(tvId: String, favorite: Boolean, isFromSearch: Boolean) {
        _tvShowUiState.update { currentState ->
            if (isFromSearch) {
                val list = currentState.searchList
                list.find { it.id == tvId.toInt() }?.favorite = favorite
                currentState.copy(
                    searchList = list, favoriteChangeRequest = "Requested"
                )
            } else {
                val list = currentState.trendingTvShows
                list.find { it.id == tvId.toInt() }?.favorite = favorite
                currentState.copy(
                    trendingTvShows = list, favoriteChangeRequest = "Requested"
                )
            }
        }
        viewModelScope.launch {
            repository.addToFavorite(tvId, favorite = favorite)
        }
    }

    private fun updateFavoriteValues(tvShowItems: List<TvShowsResponse.TvShowItem>): List<TvShowsResponse.TvShowItem> {
        viewModelScope.launch {
            val map = repository.getFavoriteList()
            for ((key, value) in map) {
                tvShowItems.find { it.id == key.toInt() }?.favorite = value
            }
        }
        return tvShowItems
    }

    private fun updateFavoriteValuesForSearch(tvShowItems: List<SearchResponse.SearchItem>): List<SearchResponse.SearchItem> {
        viewModelScope.launch {
            val map = repository.getFavoriteList()
            for ((key, value) in map) {
                tvShowItems.find { it.id == key.toInt() }?.favorite = value
            }
        }
        return tvShowItems
    }

    fun updateFavorite() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                favoriteChangeRequest = null
            )
        }
    }

    fun onPopupDismiss() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                failureMessage = null
            )
        }
    }

    fun updateSearchFavoriteList() {
        _tvShowUiState.update { currentState ->
            currentState.copy(
                searchList = updateFavoriteValuesForSearch(currentState.searchList),
                favoriteChangeRequest = "Changed"
            )
        }
    }
}

@Immutable
data class TvShowUiState(
    val trendingTvShows: List<TvShowsResponse.TvShowItem> = emptyList(),
    val searchList: List<SearchResponse.SearchItem> = emptyList(),
    val isSearching: Boolean = false,
    val favoriteChangeRequest: String? = null,
    val isTrendingTvShowApiLoading: Boolean = false,
    val searchText: String = "",
    val failureMessage: String? = null
)
