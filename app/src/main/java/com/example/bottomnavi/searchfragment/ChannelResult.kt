package com.example.bottomnavi.searchfragment

import com.example.bottomnavi.retrofit.Standard
import com.google.gson.annotations.SerializedName

data class ChannelResult(
    @SerializedName("items")
    val items: List<ChItems>
)

data class ChItems(
    @SerializedName("snippet")
    val snippet: ChSnippet
)

data class ChSnippet(
    val thumbnails: ChThumbnails
)

data class ChThumbnails(
    val default: ChKey,
    val medium: ChKey
)

data class ChKey(
    val url: String
)
