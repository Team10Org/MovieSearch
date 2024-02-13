package com.example.bottomnavi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.FragmentDetailBinding
import com.example.bottomnavi.homefragment.HomeFragment.Companion.likeList
import com.example.bottomnavi.homefragment.MyVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(data: MyVideo.MyVideoItems): DetailFragment {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val data = arguments?.getParcelable<MyVideo.MyVideoItems>("videoItem")
        with(binding) {
            itemTitle.text = data?.title
            itemUplodar.text = data?.channelTitle
            itemViewCount.text = data?.views.toString()
            itemPublished.text = data?.publishedAt
            itemContent.text = data?.content
            if (data?.isLike == false) {
                itemIsLike.setImageResource(R.drawable.empty_heart)
            } else {
                itemIsLike.setImageResource(R.drawable.heart)
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
        Glide.with(binding.root).load(data?.thumbnail).into(binding.itemPicture)

        val myItem = MyVideo.MyVideoItems(
            data?.videoUri,
            data?.title,
            data?.thumbnail,
            data?.content,
            true,
            data?.views,
            data?.tags,
            data?.channelTitle,
            data?.publishedAt
        )
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
                likeList.add(myItem)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}