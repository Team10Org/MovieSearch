package com.example.bottomnavi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.FragmentDetailBinding
import com.example.bottomnavi.homefragment.HomeFragment.Companion.likeList
import com.example.bottomnavi.homefragment.MyVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(data: MyVideo): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable("videoItem", data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        val data = arguments?.getParcelable<MyVideo.MyVideoItems>("videoItem")
        with(binding) {
            itemTitle.text = data?.title
            itemUplodar.text = data?.channelTitle
            itemViewCount.text = "%,d".format(data?.views?.toLong())+"회"
            val parsed = OffsetDateTime.parse(data?.publishedAt)
            val formatter = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            itemPublished.text = formatter
            itemContent.text = data?.content
            if (data?.isLike == false) {
                itemIsLike.setImageResource(R.drawable.empty_heart)
            } else {
                itemIsLike.setImageResource(R.drawable.heart)
            }
            itemShare.setOnClickListener {
                val videoUrl = data?.videoUri
                if(!videoUrl.isNullOrBlank()){
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, videoUrl)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                } else{

                }
            }
        }
        val youtubePlayer = binding.youtubePlayerView
        lifecycle.addObserver(youtubePlayer)
        youtubePlayer.addYouTubePlayerListener(object :
        AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data?.videoUri
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        })
        Glide.with(binding.root).load(data?.channelImage).into(binding.itemPicture)

        val myItem = data?.views?.let {
            MyVideo.MyVideoItems(
                data?.videoUri,
                data?.title,
                data?.thumbnail,
                data?.content,
                true,
                it,
                data?.tags,
                data?.channelTitle,
                data?.publishedAt,
                data?.channelImage
            )
        }
        if(likeList.contains(myItem)){
            binding.itemIsLike.setImageResource(R.drawable.heart)
        } else{
            binding.itemIsLike.setImageResource(R.drawable.empty_heart)
        }
        binding.itemIsLike.setOnClickListener {
            if (likeList.contains(myItem)) {
                binding.itemIsLike.setImageResource(R.drawable.empty_heart)
                likeList.remove(myItem)
            } else {
                binding.itemIsLike.setImageResource(R.drawable.heart)
                if (myItem != null) {
                    likeList.add(myItem)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}