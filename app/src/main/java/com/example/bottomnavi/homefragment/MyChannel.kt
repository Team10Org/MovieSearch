package com.example.bottomnavi.homefragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
sealed interface MyChannel : Parcelable {
    @Parcelize
    data class MyChannelItems(
        val thumbnail: String?,
        val channelId: String?,
    ) : MyChannel
}