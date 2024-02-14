package com.example.bottomnavi.searchfragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

sealed interface MySearchItem : Parcelable {
    @Parcelize
    data class SearchItem(
        val videoUri: String?,
        var title: String,
        var thumbnail: String,
        var pfp: String,
        var uploader: String,
        var uploadTime: String,
        var views: String,
        var isLike: Boolean,
        var tags: List<String>?,
        var content: String
    ) : MySearchItem
}