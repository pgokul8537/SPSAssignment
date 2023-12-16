package com.example.spsassignment.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.spsassignment.network.model.ImageItem
import com.example.spsassignment.network.model.TvShowDetailsResponse
import com.example.spsassignment.network.model.TvShowsResponse
import com.example.spsassignment.ui.components.DetailScreenTopBar
import com.example.spsassignment.ui.components.SimilarTvShowUiItem
import com.example.spsassignment.ui.components.TvDetailsTopItem
import com.example.spsassignment.ui.components.TvShowSeasonListItem
import com.example.spsassignment.ui.components.common.GenericDialog
import com.example.spsassignment.ui.components.common.TextWithIcon
import com.example.spsassignment.ui.components.shimmerEffect
import com.example.spsassignment.utils.FormatUtil.getFormattedRating
import com.example.spsassignment.viewmodel.TvDetailsViewModel

@Composable
fun TVDetailsScreen(
    viewModel: TvDetailsViewModel = hiltViewModel(),
    tvId: Int?,
    onBackClick: () -> Unit,
    onTVClick: (tvShowId: Int) -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        tvId?.let {
            viewModel.getTvShowDetails(it)
            viewModel.getSimilarTvShows(it)
        }
    })
    val tvShowDetailsUiState by viewModel.tvShowDetailsUiState.collectAsState()
    LaunchedEffect(key1 = tvShowDetailsUiState.favoriteChangeRequest, block = {
        if (!tvShowDetailsUiState.favoriteChangeRequest.isNullOrEmpty()) {
            viewModel.updateFavorite()
        }
    })
    TvDetailsScreen(failureMessage = tvShowDetailsUiState.failureMessage,
        tvResponse = tvShowDetailsUiState.tvShowDetailsResponse,
        similarTVShowResponse = tvShowDetailsUiState.similarTvShowsResponse,
        onBackClick = onBackClick,
        onTVClick = onTVClick,
        addToFavoriteClick = { tvId, isFavorite ->
            viewModel.addToFavorite(tvId.toString(), isFavorite)
        },
        isFavorite = tvShowDetailsUiState.isFavorite,
        addToFavoriteSimilarListClick = { tvId, isFavorite ->
            viewModel.addToFavoriteForSimilarListing(tvId.toString(), isFavorite)
        }, onPopupDismiss = {
            viewModel.onPopupDismiss()
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun TvDetailsScreen(
    failureMessage: String?,
    tvResponse: TvShowDetailsResponse?,
    similarTVShowResponse: List<TvShowsResponse.TvShowItem>,
    onBackClick: () -> Unit,
    addToFavoriteClick: (tvId: Int, Boolean) -> Unit,
    addToFavoriteSimilarListClick: (tvId: Int, Boolean) -> Unit,
    onPopupDismiss: () -> Unit,
    onTVClick: (tvId: Int) -> Unit, isFavorite: Boolean
) {
    val horizontalListViewLazyListState = rememberLazyListState()

    Scaffold(topBar = {
        DetailScreenAppBar(onBackClick, addToFavoriteClick = { favorite ->
            tvResponse?.id?.let {
                addToFavoriteClick.invoke(
                    it, favorite
                )
            }
        }, isFavorite)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .verticalScroll(rememberScrollState())
        ) {
            if (!failureMessage.isNullOrEmpty()) {
                GenericDialog(
                    message = failureMessage,
                    onPositiveButtonClickAction = { },
                    popUpVisibleState = {
                        onPopupDismiss.invoke()
                    },
                    shouldCloseOnTouchOutside = false
                )
            }
            tvResponse?.let {
                it.posterPath?.let { images ->
                    TvDetailsTopItem(
                        listOf(ImageItem(tvResponse.posterPath)), tvResponse.name
                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            FlowRow(maxItemsInEachRow = 2) {
                                tvResponse.genres?.forEach { item ->
                                    Surface(
                                        modifier = Modifier.padding(5.dp),
                                        shape = CircleShape,
                                        color = Color.LightGray.copy(0.5f)
                                    ) {
                                        item?.name?.let { it1 ->
                                            Text(
                                                text = it1,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                modifier = Modifier.padding(
                                                    top = 5.dp,
                                                    bottom = 5.dp,
                                                    start = 10.dp,
                                                    end = 10.dp
                                                ),
                                                textAlign = TextAlign.Start,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.padding(top = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextWithIcon(
                                    Modifier.padding(top = 10.dp),
                                    Icons.Filled.Star,
                                    Color.Red,
                                    getFormattedRating(tvResponse.voteAverage)
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                            }
                        }

                        AsyncImage(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .padding(start = 5.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .shimmerEffect(),
                            contentScale = ContentScale.FillBounds,
                            model = tvResponse.getDefaultImagePath(),
                            contentDescription = ""
                        )
                    }

                }
                Spacer(modifier = Modifier.size(10.dp))
                tvResponse.overview?.let { overview ->
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Overview",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp, end = 16.dp
                        ),
                        text = overview,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                if (!tvResponse.seasons.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Seasons",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    LazyRow(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 16.dp, horizontal = 10.dp),
                        contentPadding = PaddingValues(
                            horizontal = 20.dp, vertical = 12.dp
                        ),
                        state = horizontalListViewLazyListState,
                        flingBehavior = rememberSnapFlingBehavior(
                            lazyListState = horizontalListViewLazyListState
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        content = {
                            itemsIndexed(tvResponse.seasons) { index, season ->
                                Card(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .height(160.dp)
                                        .animateContentSize(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                        contentColor = Color.Black,
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 0.dp,
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                ) {
                                    TvShowSeasonListItem(
                                        season?.episodeCount,
                                        season?.getDefaultImagePath(),
                                        index + 1
                                    )
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                SimilarTvShowUiItem(
                    similarTVShowResponse,
                    "Similar Tv Shows", itemClick = { tvShowId ->
                        tvShowId?.let {
                            onTVClick(it)
                        }
                    }, onFavoriteClick = { tvShowId, favorite ->
                        addToFavoriteSimilarListClick.invoke(
                            tvShowId, favorite
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))

            }
        }
    }
}

@Composable
fun DetailScreenAppBar(
    onBackClick: () -> Unit,
    addToFavoriteClick: (Boolean) -> Unit,
    isFavorite: Boolean
) {
    val isFavoriteValue = rememberUpdatedState(newValue = isFavorite)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        ) {
        DetailScreenTopBar(
            onBackClick = onBackClick, addToFavoriteClick = {
                addToFavoriteClick.invoke(it)
            }, isFavoriteValue
        )
    }
}