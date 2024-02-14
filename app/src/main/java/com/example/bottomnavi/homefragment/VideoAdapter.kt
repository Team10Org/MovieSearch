package com.example.bottomnavi.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.DetailFragment
import com.example.bottomnavi.R
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
            VideoItemViewHolder(
                VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onClickItem
            )

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
                val myData = MyVideo.MyVideoItems(
                    item.videoUri,
                    item.title,
                    item.thumbnail,
                    item.content,
                    item.isLike,
                    item.views,
                    item.tags,
                    item.channelTitle,
                    item.publishedAt
                )
                val bundle = Bundle().apply {
                    putParcelable("videoItem", myData)
                }
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle
                val transaction = (container.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.slide_enter_from_left, R.anim.slide_exit_to_left, R.anim.slide_enter_from_left, R.anim.slide_exit_to_left)
                transaction.replace(R.id.linearLayout, detailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}
