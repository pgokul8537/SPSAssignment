package com.example.spsassignment.network.model

import android.os.Parcelable
import com.example.spsassignment.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(
    val filePath: String?,
) : Parcelable {
    fun getDefaultImagePath(): String? {
        if (!filePath.isNullOrEmpty()) {
            return "${Constants.imageUrl}$filePath"
        }
        return null
    }

    fun getOriginalImagePath(): String? {
        if (!filePath.isNullOrEmpty()) {
            return "${Constants.originalImageUrl}$filePath"
        }
        return null
    }
}