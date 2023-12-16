package com.example.spsassignment.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spsassignment.network.DataHandler
import com.example.spsassignment.network.model.TvShowDetailsResponse
import com.example.spsassignment.network.model.TvShowsResponse
import com.example.spsassignment.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    val repository: TvRepository
) : ViewModel() {
    private val _tvShowDetailsUiState = MutableStateFlow(TvShowDetailsUiState())
    val tvShowDetailsUiState = _tvShowDetailsUiState.asStateFlow()
    fun getTvShowDetails(tvId: Int) {
        viewModelScope.launch {
            getTvShowDetailsResponse(repository.getTvShowDetails(tvId.toString())) { tvShowItems, errorMessage ->
                _tvShowDetailsUiState.update { currentState ->
                    currentState.copy(
                        failureMessage = errorMessage,
                        tvShowDetailsResponse = tvShowItems,
                        isApiLoading = false, isFavorite = getFavorite(tvId.toString())
                    )
                }
            }
        }
    }

    fun getSimilarTvShows(tvId: Int) {
        viewModelScope.launch {
            getSimilarTvShowsResponse(repository.getSimilarTvShows(tvId.toString())) { tvShowItems, errorMessage ->
                _tvShowDetailsUiState.update { currentState ->
                    currentState.copy(
                        failureMessage = errorMessage,
                        similarTvShowsResponse = updateFavoriteValues(tvShowItems),
                        isSimilarTvShowsApiLoading = false
                    )
                }
            }
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

    private fun getSimilarTvShowsResponse(
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

    private fun getTvShowDetailsResponse(
        responseResult: DataHandler<TvShowDetailsResponse>,
        onResult: (TvShowDetailsResponse?, String) -> Unit,

        ) {
        when (responseResult) {
            is DataHandler.Failure -> {
                onResult(null, responseResult.message)
            }

            is DataHandler.NoInternetConnectionFailure -> {
                onResult(null, responseResult.message)
            }

            is DataHandler.Success -> {
                onResult(responseResult.data, "")
            }
        }
    }

    fun addToFavorite(tvId: String, favorite: Boolean) {
        viewModelScope.launch {
            repository.addToFavorite(tvId, favorite = favorite)
        }
        _tvShowDetailsUiState.update { currentState ->
            currentState.copy(
                isFavorite = favorite, favoriteChangeRequest = "Requested"
            )
        }
    }

    fun addToFavoriteForSimilarListing(tvId: String, favorite: Boolean) {
        _tvShowDetailsUiState.update { currentState ->
            val list = currentState.similarTvShowsResponse
            list.find { it.id == tvId.toInt() }?.favorite = favorite
            currentState.copy(
                similarTvShowsResponse = list, favoriteChangeRequest = "Requested"
            )
        }
        viewModelScope.launch {
            repository.addToFavorite(tvId, favorite = favorite)
        }
    }

    private fun getFavorite(tvId: String): Boolean {
        var favorite = false
        viewModelScope.launch {
            val map = repository.getFavoriteList()
            favorite = map[tvId] == true
        }
        return favorite
    }

    fun updateFavorite() {
        _tvShowDetailsUiState.update { currentState ->
            currentState.copy(
                favoriteChangeRequest = null
            )
        }
    }

    fun onPopupDismiss() {
        _tvShowDetailsUiState.update { currentState ->
            currentState.copy(
                failureMessage = null
            )
        }
    }
}

@Immutable
data class TvShowDetailsUiState(
    val tvShowDetailsResponse: TvShowDetailsResponse? = null,
    val similarTvShowsResponse: List<TvShowsResponse.TvShowItem> = emptyList(),
    val isApiLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val favoriteChangeRequest: String? = null,
    val isSimilarTvShowsApiLoading: Boolean = true,
    val failureMessage: String? = null
)
