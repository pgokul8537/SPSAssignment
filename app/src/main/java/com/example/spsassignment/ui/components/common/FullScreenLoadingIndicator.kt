package com.example.spsassignment.ui.components.common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@Composable
fun FullScreenLoadingIndicator(
    showLoadingIndicator: Boolean,
) {
    if (showLoadingIndicator) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(color = Color.Red)
        }
    }
}