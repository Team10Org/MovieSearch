package com.example.bottomnavi.searchfragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class SearchItem(
    val videoUri: String?,
    var title: String,
    var thumbnail: String,
    var uploader: String,
    var uploadTime: String,
    var views: String
) : Parcelable