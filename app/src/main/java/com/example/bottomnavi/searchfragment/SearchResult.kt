package com.example.bottomnavi.searchfragment

import com.google.gson.annotations.SerializedName

data class SearchResult(
    val regionCode: String,
    @SerializedName("items")
    val items: List<SearchItems>
)

data class SearchItems(
    val kind: String,
    val etag: String,
    val id: Id,
    @SerializedName("snippet")
    val snippet: Snippet
)

data class Id(
    val videoId: String,
)

data class Snippet(
    val publishedAt: String,
    val title: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val channelId: String
)

data class Thumbnails(
    val default: Key,
    val medium: Key
)

data class Key(
    val url: String
)