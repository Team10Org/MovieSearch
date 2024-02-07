package com.example.bottomnavi.retrofit

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET(Contract.REQUEST_ADDRESS)
    suspend fun getYoutubeVideo(
        @QueryMap param: HashMap<String, String>
    ): SearchResponse
}