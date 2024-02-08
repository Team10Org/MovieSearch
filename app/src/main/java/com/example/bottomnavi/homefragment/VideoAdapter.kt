package com.example.bottomnavi.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.UnknownItemBinding
import com.example.bottomnavi.databinding.VideoItemBinding

class VideoAdapter(
    private val onClickItem: (Int, MyVideo) -> Unit,
) : ListAdapter<MyVideo, VideoAdapter.VideoViewHolder>(
    object : DiffUtil.ItemCallback<MyVideo>(){
        override fun areItemsTheSame(
            oldItem: MyVideo,
            newItem: MyVideo
        ): Boolean = if(oldItem is MyVideo.MyVideoItems && newItem is MyVideo.MyVideoItems){
            oldItem.videoUri == newItem.videoUri
        } else{
            oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: MyVideo,
            newItem: MyVideo
        ): Boolean = oldItem == newItem
    }
){
    abstract class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun onBind(item: MyVideo)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MyVideo.MyVideoItems -> VideoListViewType.ITEM
        else -> VideoListViewType.UNKNOWN
    }.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        when(VideoListViewType.from(viewType)){
            VideoListViewType.ITEM -> VideoItemViewHolder(
                VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onClickItem
            )
            else -> VideoUnknownViewHolder(
                UnknownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }



    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class VideoItemViewHolder(
        private val binding: VideoItemBinding,
        private val onClickItem: (Int, MyVideo) -> Unit
    ) : VideoViewHolder(binding.root){
        override fun onBind(item: MyVideo) = with(binding){
            if(item !is MyVideo.MyVideoItems) {
                return@with
            }
            videoTitle.text = item.title
            Glide.with(binding.root).load(item.thumbnail).into(binding.videoImage)

            container.setOnClickListener{
                onClickItem(
                    adapterPosition,
                    item
                )
            }
        }
    }
    class VideoUnknownViewHolder(
        binding: UnknownItemBinding
    ) : VideoViewHolder(binding.root) {
        override fun onBind(item: MyVideo) = Unit
    }
}
