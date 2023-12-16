package com.example.spsassignment.ui.components.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spsassignment.R

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    onFavoriteChanged: (isFavorite: Boolean) -> Unit,
    isFavorite: Boolean,
    targetValue:Float
) {

    val targetScale = if (isFavorite) targetValue else 1.0f
    val animatedScale by animateFloatAsState(targetScale, label = "")
    IconToggleButton(

        checked = isFavorite,
        onCheckedChange = {
            onFavoriteChanged(!isFavorite)
        },
        modifier = modifier
            .background(Color.Transparent)
            .size(30.dp)
    ) {
        if (isFavorite) {
            Image(painter = painterResource(R.drawable.ic_favorite), contentDescription = "",
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                    })
        } else {
            Icon(imageVector = Icons.Default.FavoriteBorder,
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = animatedScale
                        scaleY = animatedScale
                    })
        }
    }
}

@Preview
@Composable
fun FavoriteButtonPreview() {
    var isChecked by remember {
        mutableStateOf(true)
    }
    val isFavorite = remember { mutableStateOf(false) }
    FavoriteButton(onFavoriteChanged = { isFavorite ->
    }, isFavorite = true, targetValue = 0.9f)
}