package com.example.spsassignment.ui.components

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spsassignment.network.model.SearchResponse
import com.example.spsassignment.ui.screens.Progress

@Composable
fun SearchUiItem(
    list: List<SearchResponse.SearchItem>,
    title: String,
    itemClick: (tvId: Int?) -> Unit,
    onFavoriteClick: (tvId: Int, favorite: Boolean) -> Unit
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
        Spacer(modifier = Modifier.size(16.dp))
        if (list.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                flingBehavior = ScrollableDefaults.flingBehavior(),
            ) {
                items(list) { item ->
                    TvShowListItem(
                        item.name,
                        item.id,
                        item.voteAverage,
                        item.getDefaultImagePath(), onItemClick = { tvId ->
                            itemClick.invoke(tvId)
                        }, onFavoriteClick = { tvId, favorite ->
                            onFavoriteClick.invoke(tvId, favorite)
                        }, favourite = item.favorite
                    )
                }
            }

        }
    }


}

