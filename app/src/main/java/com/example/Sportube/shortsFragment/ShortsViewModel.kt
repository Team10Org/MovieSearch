package com.example.Sportube.shortsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Sportube.BuildConfig
import com.example.Sportube.homefragment.HomeFragment
import com.example.Sportube.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class ShortsViewModel : ViewModel() {
    private val _searchParam: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    val searchParam: LiveData<HashMap<String, String>> get() = _searchParam
    private val _searchResult: MutableLiveData<List<ShortsItems>> = MutableLiveData()
    val searchResult: LiveData<List<ShortsItems>> get() = _searchResult
    private val authKey = BuildConfig.youtube_api_key

    fun setUpShortsParameter() {
        _searchParam.value = hashMapOf(
            "key" to authKey,
            "part" to "snippet,statistics,contentDetails",
            "chart" to "mostPopular",
            "maxResults" to "49",
            "regionCode" to "kr",
            "videoCategoryId" to "17"
        )
    }
    fun communicateNetWork(param: HashMap<String, String>){
        viewModelScope.launch() {
            val responseData = NetWorkClient.youtubeNetWork.getYoutubeVideo(param)
            val searchItems = responseData.items

            if (searchItems.isNotEmpty()) {
                for (item in searchItems) {
                    if(item.contentDetails.duration.contains("M")){
                        continue
                    }
                    val shortsItem = ShortsItems(
                        id = item.id,
                        duration = item.contentDetails.duration,
                        title = item.snippet.title,
                        content = item.snippet.description
                    )
                    HomeFragment.shortsList.add(shortsItem)
                }
                _searchResult.value = HomeFragment.shortsList
            }
        }
    }
}