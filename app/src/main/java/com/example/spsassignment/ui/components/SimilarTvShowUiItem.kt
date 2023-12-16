package com.example.spsassignment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spsassignment.network.model.TvShowsResponse

@Composable
fun SimilarTvShowUiItem(
    similarList: List<TvShowsResponse.TvShowItem>,
    title: String,
    itemClick: (tvId: Int?) -> Unit, onFavoriteClick: (tvId: Int, favorite: Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
        }
        if (similarList.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(similarList) { item ->
                        TvShowListItem(
                            item.name,
                            item.id,
                            item.voteAverage,
                            item.getDefaultImagePath(),
                            onItemClick = { tvId ->
                                itemClick.invoke(tvId)
                            }, onFavoriteClick = { tvId, favorite ->
                                onFavoriteClick.invoke(tvId, favorite)
                            }, favourite = item.favorite
                        )
                    }
                }
            )
        }
    }
}