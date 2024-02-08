package com.example.bottomnavi.searchfragment

import android.os.Build
import com.example.bottomnavi.searchfragment.SearchItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.ItemSearchBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchAdapter(
    private val onClickItem: (Int, SearchItem) -> Unit,
) : ListAdapter<SearchItem, SearchAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position)
        holder.bind(videoItem)
        holder.itemView.setOnClickListener {
            onClickItem(position, videoItem)
        }
    }

    class VideoViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(videoItem: SearchItem) {
            binding.tvVideoTitle.text = videoItem.title
            TODO("체널 프로필 사진이 안 나오는 것 고치기")
            Glide.with(binding.root).load(videoItem.thumbnail).into(binding.imgThumbnail)
            Glide.with(binding.root).load(videoItem.pfp).into(binding.imgPfp)
            binding.tvViewCount.text = "%,d".format(videoItem.views.toLong()) + "회"
            binding.tvVideoTitle.text = videoItem.title
            binding.tvVideoOther.text = videoItem.uploader+"  "+videoItem.uploadTime.take(10)
        }
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.videoUri == newItem.videoUri
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }
}
