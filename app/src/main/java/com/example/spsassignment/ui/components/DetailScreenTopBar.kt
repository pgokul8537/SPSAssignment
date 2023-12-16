package com.example.spsassignment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spsassignment.ui.components.common.FavoriteButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(
    onBackClick: () -> Unit, addToFavoriteClick: (Boolean) -> Unit, isFavorite: State<Boolean>
) {
    TopAppBar(
        title = {
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(Color.Transparent.copy(alpha = 0.1f)),
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onBackClick.invoke() },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }, actions = {
            FavoriteButton(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = 10.dp),
                isFavorite = isFavorite.value,
                onFavoriteChanged = { isFavorite ->
                    addToFavoriteClick.invoke(isFavorite)
                }, targetValue = 1.08f
            )
        }
    )
}