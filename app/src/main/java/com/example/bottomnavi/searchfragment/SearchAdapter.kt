package com.example.bottomnavi.searchfragment

import com.example.bottomnavi.searchfragment.SearchItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.ItemSearchBinding

class SearchAdapter(
    private val onClickItem: (Int, SearchItem) -> Unit,
) : ListAdapter<SearchItem, SearchAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position)
        holder.bind(videoItem)
        holder.itemView.setOnClickListener {
            onClickItem(position, videoItem)
        }
    }

    class VideoViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoItem: SearchItem) {
            binding.tvVideoTitle.text = videoItem.title
            Glide.with(binding.root).load(videoItem.thumbnail).into(binding.imgThumbnail)
            binding.tvViewCount.text = videoItem.views
            binding.tvVideoTitle.text = videoItem.title
            binding.tvVideoOther.text = videoItem.uploader+" "+videoItem.uploadTime
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
