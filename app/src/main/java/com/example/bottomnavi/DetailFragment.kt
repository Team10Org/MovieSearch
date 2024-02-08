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
        }
        Glide.with(binding.root).load(data?.thumbnail).into(binding.itemThumbnail)
        Glide.with(binding.root).load(data?.thumbnail).into(binding.itemPicture)

        binding.itemIsLike.setOnClickListener {
            likeList.add(
                MyVideo.MyVideoItems(
                    data?.videoUri,
                    data?.title,
                    data?.thumbnail,
                    data?.content,
                    data?.isLike,
                    data?.views,
                    data?.tags,
                    data?.channelTitle,
                    data?.publishedAt
                )
            )
            Log.d("Detail","likeList = ${likeList}")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}