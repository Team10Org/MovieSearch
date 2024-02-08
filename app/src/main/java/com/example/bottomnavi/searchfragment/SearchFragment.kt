package com.example.bottomnavi.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavi.BuildConfig
import com.example.bottomnavi.databinding.FragmentSearchBinding
import com.example.bottomnavi.retrofit.NetWorkClient
import com.example.bottomnavi.retrofit.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchNetWorkInterface {
    @GET("search")
    suspend fun getYoutubeVideo(
        @QueryMap param: HashMap<String, String>
    ): SearchResult
}

interface ChannelNetWorkInterface {
    @GET("channels")
    suspend fun getYoutubeVideo(
        @QueryMap param: HashMap<String, String>
    ): ChannelResult
}

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var videoList: ArrayList<SearchItem> = ArrayList()
    private lateinit var adapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = SearchAdapter() { position, videoItem -> }
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.tvSearchBtn.setOnClickListener {
            communicateNetWork(hashMapOf(
                "key" to BuildConfig.youtube_api_key,
                "part" to "snippet",
                "maxResults" to "25",
                "q" to binding.etSearch.text.toString(),
                "type" to "video"
            ))
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun communicateNetWork(param: HashMap<String, String>) =
        lifecycleScope.launch() {

            val responseData = NetWorkClient.youtubeRetrofit.create(SearchNetWorkInterface::class.java).getYoutubeVideo(param)
            val searchItems = responseData.items

            if (searchItems.isNotEmpty()) {
                for (item in searchItems) {
                    val findviews = NetWorkClient.youtubeNetWork.getYoutubeVideo(hashMapOf(
                        "key" to BuildConfig.youtube_api_key,
                        "part" to "statistics",
                        "id" to item.id.videoId,
                        "regionCode" to "kr"
                    ))
                    val findpfp = NetWorkClient.youtubeRetrofit.create(ChannelNetWorkInterface::class.java).getYoutubeVideo(
                        hashMapOf(
                            "key" to BuildConfig.youtube_api_key,
                            "part" to "snippet",
                            "id" to item.snippet.channelId
                        )
                    )
                    val videoItem = SearchItem(
                        videoUri = item.id.videoId,
                        title = item.snippet.title,
                        thumbnail = item.snippet.thumbnails.medium.url,
                        pfp = findpfp.items[0].snippet.thumbnails.medium.url,
                        uploader = item.snippet.channelTitle,
                        uploadTime = item.snippet.publishedAt,
                        views = findviews.items[0].statistics.viewCount
                    )
                    videoList.add(videoItem)
                }
                withContext(Dispatchers.Main) {
                    adapter.submitList(videoList.toList())
                }

            }
        }
}