package com.example.bottomnavi.homefragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class MyVideoItems(
    val videoUri: String?,
    var title: String,
    var thumbnail: String,
    var content: String,
    var isLike: Boolean,
    var views: Int
) : Parcelable