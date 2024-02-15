package com.example.Sportube.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Sportube.BuildConfig
import com.example.Sportube.R
import com.example.Sportube.databinding.FragmentSearchBinding
import com.example.Sportube.homefragment.VideoAdapter
import com.example.Sportube.retrofit.NetWorkClient
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
    private var videoList: ArrayList<MySearchItem.SearchItem> = ArrayList()
    private lateinit var adapter: SearchAdapter

    private val listAdapter: VideoAdapter by lazy {
        VideoAdapter(
            onClickItem = { position, item ->

            }
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = SearchAdapter { position, item -> }

        binding.etSearch.setText("")
        binding.tvSearch.text=getString(R.string.search_fragment_put_value)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.tvSearchBtn.setOnClickListener {
            communicateNetWork(hashMapOf(
                "key" to BuildConfig.youtube_api_key,
                "part" to "snippet",
                "maxResults" to "10",
                "q" to binding.etSearch.text.toString(),
                "topicId" to "/m/06ntj",
                "type" to "video",
                "videoCategoryId" to "17"
            ))
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun communicateNetWork(param: HashMap<String, String>) =
        lifecycleScope.launch() {
            videoList= ArrayList()

            val responseData = NetWorkClient.youtubeRetrofit.create(SearchNetWorkInterface::class.java).getYoutubeVideo(param)
            val searchItems = responseData.items

            if (searchItems.isNotEmpty()) {
                binding.tvSearch.text=""
                for (item in searchItems) {
                    val findviews = NetWorkClient.youtubeNetWork.getYoutubeVideo(hashMapOf(
                        "key" to BuildConfig.youtube_api_key,
                        "part" to "snippet,statistics",
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
                    val videoItem = MySearchItem.SearchItem(
                        videoUri = item.id.videoId,
                        title = Html.fromHtml(item.snippet.title, Html.FROM_HTML_MODE_LEGACY).toString(),
                        thumbnail = item.snippet.thumbnails.medium.url,
                        pfp = findpfp.items[0].snippet.thumbnails.medium.url,
                        uploader = Html.fromHtml(item.snippet.channelTitle, Html.FROM_HTML_MODE_LEGACY).toString(),
                        uploadTime = item.snippet.publishedAt,
                        views = findviews.items[0].statistics.viewCount,
                        isLike = false,
                        content = findviews.items[0].snippet.description,
                        tags = findviews.items[0].snippet.tags
                    )
                    videoList.add(videoItem)
                }
                withContext(Dispatchers.Main) {
                    adapter.submitList(videoList.toList())
                }

            }
            else{
                binding.tvSearch.text= getString(R.string.search_fragment_no_value)
            }
        }
}