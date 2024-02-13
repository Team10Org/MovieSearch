package com.example.bottomnavi.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("videos")
    suspend fun getYoutubeVideo(
        @QueryMap param: HashMap<String, String>
    ): SearchResponse
    @GET("channels")
    suspend fun channelByCategory(
        @QueryMap param: HashMap<String, String>
    ) : Channel
}