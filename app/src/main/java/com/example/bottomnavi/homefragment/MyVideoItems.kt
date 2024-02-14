package com.example.bottomnavi.homefragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed interface MyVideo : Parcelable{
    @Parcelize
    data class MyVideoItems(
        val videoUri: String?,
        var title: String?,
        var thumbnail: String?,
        var content: String?,
        var isLike: Boolean? = false,
        var views: Long,
        var tags: List<String>?,
        val channelTitle: String?,
        val publishedAt: String?,
    ) : MyVideo
}