package com.example.spsassignment.ui.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.spsassignment.ui.components.LifecycleEventEffect
import com.example.spsassignment.ui.components.SearchUiItem
import com.example.spsassignment.ui.components.TvShowUiItem
import com.example.spsassignment.ui.components.common.FullScreenLoadingIndicator
import com.example.spsassignment.ui.components.common.GenericDialog
import com.example.spsassignment.viewmodel.TvViewModel

@Composable
fun TvShowsScreen(viewModel: TvViewModel = hiltViewModel(), onTVClick: (tvShowId: Int) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getTrendingTvShows()
    }

    val tvShowUiState by viewModel.tvShowUiState.collectAsState()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = tvShowUiState.favoriteChangeRequest, block = {
        if (!tvShowUiState.favoriteChangeRequest.isNullOrEmpty()) {
            viewModel.updateFavorite()
        }
    })
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.updateSearchFavoriteList()
    }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!tvShowUiState.failureMessage.isNullOrEmpty()) {
                GenericDialog(
                    message = tvShowUiState.failureMessage,
                    onPositiveButtonClickAction = { },
                    popUpVisibleState = {
                        viewModel.onPopupDismiss()
                    },
                    shouldCloseOnTouchOutside = false
                )
            }
            TextField(
                value = tvShowUiState.searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search") },
                trailingIcon = {
                    if (tvShowUiState.searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.clearText()
                        }) {
                            Icon(
                                Icons.Default.Close, contentDescription = ""
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    if (tvShowUiState.searchText.isNotEmpty())
                        viewModel.getSearchData()
                }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )
            Spacer(modifier = Modifier.size(16.dp))
            FullScreenLoadingIndicator(tvShowUiState.isSearching)
            AnimatedVisibility(visible = tvShowUiState.searchList.isEmpty()) {
                TvShowUiItem(
                    tvShowUiState.trendingTvShows,
                    "Trending This Week",
                    itemClick = { tvShowId ->
                        tvShowId?.let {
                            onTVClick(it)
                        }
                    },
                    onFavoriteClick = { tvShowId, favorite ->
                        viewModel.addToFavorite(tvShowId.toString(), favorite, false)
                    }, isLoading = tvShowUiState.isTrendingTvShowApiLoading
                )
            }
            AnimatedVisibility(visible = tvShowUiState.searchList.isNotEmpty()) {
                SearchUiItem(
                    tvShowUiState.searchList,
                    "Tv Shows Search Results",
                    itemClick = { tvShowId ->
                        tvShowId?.let {
                            onTVClick(tvShowId)
                        }
                    },
                    onFavoriteClick = { tvShowId, favorite ->
                        viewModel.addToFavorite(tvShowId.toString(), favorite, true)
                    }
                )
            }

        }
    }
    val context = LocalContext.current
    BackHandler {
        context.findActivity()?.finish()
    }
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.Red)
    }

}