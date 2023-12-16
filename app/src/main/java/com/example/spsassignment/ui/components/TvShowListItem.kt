package com.example.spsassignment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spsassignment.ui.components.common.FavoriteButton
import com.example.spsassignment.ui.components.common.TextWithIcon
import com.example.spsassignment.utils.FormatUtil.getFormattedRating

@Composable
fun TvShowListItem(
    name: String?,
    id: Int?,
    voteAverage: Double?,
    imagePath: String?,
    onItemClick: (tvId: Int?) -> Unit,
    onFavoriteClick: (tvId: Int, favourite: Boolean) -> Unit,
    favourite: Boolean = false
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            .width(160.dp)
            .height(300.dp)
            .clickable { onItemClick.invoke(id) },
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(Color.Gray)
        ) {
            AsyncImage(
                model = imagePath,
                contentScale = ContentScale.FillBounds,
                contentDescription = name,
                modifier = Modifier.fillMaxSize()
            )
        }
        if (!name.isNullOrEmpty()) {
            Text(
                text = name,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 10.dp),
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextWithIcon(
                Modifier
                    .padding(top = 10.dp)
                    .wrapContentSize(),
                Icons.Filled.Star,
                Color.Red,
                getFormattedRating(voteAverage)
            )
            val checkState = rememberUpdatedState(newValue = favourite)
            println(">>>>>$favourite")
            FavoriteButton(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 20.dp),
                isFavorite = checkState.value,
                onFavoriteChanged = { isFavorite ->
                    id?.let {
                        onFavoriteClick.invoke(it, isFavorite)
                    }
                }, targetValue = 1.08f
            )
        }
    }

}