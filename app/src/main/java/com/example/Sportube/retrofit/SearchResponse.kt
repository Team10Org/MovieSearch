package com.example.Sportube.retrofit

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val kind: String,
    val etag: String,
    @SerializedName("items")
    val items: List<SearchItems>,
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)

data class SearchItems(
    val etag: String,
    val id: String,
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("statistics")
    val statistics: Statistic,
    @SerializedName("contentDetails")
    val contentDetails: contentDetail
)
data class contentDetail(
    val duration: String,
)

data class Statistic(
    val viewCount: String
)

data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val categoryId: String,
    val liveBroadcastContent: String,
    @SerializedName("localized")
    val localized: Localized,
    val defaultAudioLanguage: String,
    @SerializedName("tags")
    val tags: List<String>,
)

data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("maxres")
    val maxres: Maxres,
    @SerializedName("medium")
    val medium: Medium,
    @SerializedName("standard")
    val standard: Standard
)

data class Localized(
    val description: String,
    val title: String
)

data class Default(
    val height: Int,
    val url: String,
    val width: Int
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)

data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

data class Medium(
    val height: Int,
    val url: String,
    val width: Int
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

data class Channel(
    val kind: String,
    val etag: String,
    @SerializedName("pageInfo")
    val pageInfo: ChannelPageInfo,
    @SerializedName("items")
    val items: List<ChannelItem>
)

data class ChannelItem(
    val kind: String,
    val etag: String,
    val id: String,
    @SerializedName("snippet")
    val snippet: ChannelSnippet,
    @SerializedName("statistics")
    val statistics: ChannelStatistics
)

data class ChannelSnippet(
    val title: String,
    val description: String,
    val customURL: String,
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: ChannelThumbnails,
    @SerializedName("localized")
    val localized: ChannelLocalized,
    val country: String
)

data class ChannelLocalized(
    val title: String,
    val description: String
)

data class ChannelThumbnails(
    @SerializedName("default")
    val default: ChannelDefault,
    @SerializedName("medium")
    val medium: ChannelDefault,
    @SerializedName("high")
    val high: ChannelDefault
)

data class ChannelDefault(
    val url: String,
    val width: Int,
    val height: Int
)

data class ChannelStatistics(
    val viewCount: String,
    val subscriberCount: String,
    val hiddenSubscriberCount: Boolean,
    val videoCount: String
)

data class ChannelPageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)