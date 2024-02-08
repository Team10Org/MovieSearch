package com.example.bottomnavi.searchfragment

import com.example.bottomnavi.retrofit.PageInfo
import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import java.time.LocalDateTime

data class SearchResult(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    @SerializedName("items")
    val items: List<SearchItems>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class SearchItems(
    val kind: String,
    val etag: String,
    val id: Id,
    @SerializedName("snippet")
    val snippet: Snippet
)

data class Id(
    val videoId: String
)

data class Snippet(
    val publishedAt: String,
    val title: String,
    val thumbnails: Thumbnails,
    val channelTitle: String
)

data class Thumbnails(
    val default: Key
)

data class Key(
    val url: String
)