package com.example.spsassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.spsassignment.ui.components.common.shimmerBrush


fun Modifier.shimmerEffect(): Modifier = composed {
    background(
        shimmerBrush(true, 1300f)
    )
}