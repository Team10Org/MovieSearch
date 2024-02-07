package com.example.bottomnavi.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.VideoItemBinding

class VideoAdapter(
    private val onClickItem: (Int, MyVideoItems) -> Unit,
) : ListAdapter<MyVideoItems, VideoAdapter.VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position)
        holder.bind(videoItem)
        holder.itemView.setOnClickListener {
            onClickItem(position, videoItem)
        }
    }

    class VideoViewHolder(private val binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoItem: MyVideoItems) {
            binding.videoTitle.text = videoItem.title
            Glide.with(binding.root).load(videoItem.thumbnail).into(binding.videoImage)
        }
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<MyVideoItems>() {
        override fun areItemsTheSame(oldItem: MyVideoItems, newItem: MyVideoItems): Boolean {
            return oldItem.videoUri == newItem.videoUri
        }

        override fun areContentsTheSame(oldItem: MyVideoItems, newItem: MyVideoItems): Boolean {
            return oldItem == newItem
        }
    }
}
