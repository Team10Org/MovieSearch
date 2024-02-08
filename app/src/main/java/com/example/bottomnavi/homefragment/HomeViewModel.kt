package com.example.bottomnavi.homefragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavi.BuildConfig
import com.example.bottomnavi.homefragment.HomeFragment.Companion.videoList
import com.example.bottomnavi.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _searchParam: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    val searchParam: LiveData<HashMap<String, String>> get() = _searchParam
    private val _searchResult: MutableLiveData<List<MyVideoItems>> = MutableLiveData()
    val searchResult: LiveData<List<MyVideoItems>> get() = _searchResult
    private val _filterVideo: MutableLiveData<List<MyVideoItems>> = MutableLiveData()
    val filterVideo: LiveData<List<MyVideoItems>> get() = _filterVideo

    fun setUpVideoParameter() {
        val authKey = BuildConfig.youtube_api_key
        _searchParam.value = hashMapOf(
            "key" to authKey,
            "part" to "snippet,statistics",
            "chart" to "mostPopular",
            "maxResults" to "50",
            "regionCode" to "kr",
            "videoCategoryId" to "17"
        )
    }

    fun communicateNetWork(param: HashMap<String, String>) {
        viewModelScope.launch() {
            val responseData = NetWorkClient.youtubeNetWork.getYoutubeVideo(param)
            val searchItems = responseData.items

            if (searchItems.isNotEmpty()) {
                for (item in searchItems) {
                    val videoItem = MyVideoItems(
                        videoUri = item.id,
                        title = item.snippet.title,
                        thumbnail = item.snippet.thumbnails.default.url,
                        content = item.snippet.description,
                        isLike = false,
                        views = item.statistics.viewCount.toInt(),
                        tags = item.snippet.tags
                    )
                    videoList.add(videoItem)
                }
                Log.d("HomeViewModel", "비디어 리스트 태그확인 : $videoList")
                _searchResult.value = videoList
            } else {
                // 데이터가 없을 경우에 대한 처리
            }
        }
    }

    fun filterVideoList(selectedTag: String?) {
        if(selectedTag.isNullOrEmpty()){
            _filterVideo.value = videoList
        } else{
            _filterVideo.value = videoList.filter { myVideoItems ->
                myVideoItems.tags?.contains(selectedTag) == true
            }
        }
    }

}