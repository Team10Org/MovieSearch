package com.example.bottomnavi.searchfragment

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
import com.example.bottomnavi.databinding.ItemSearchBinding
import com.example.bottomnavi.homefragment.MyVideo

class SearchAdapter(
    private val onClickItem: (Int, MySearchItem) -> Unit,
) : ListAdapter<MySearchItem, SearchAdapter.VideoViewHolder>(
    object : DiffUtil.ItemCallback<MySearchItem>(){
        override fun areItemsTheSame(
            oldItem: MySearchItem,
            newItem: MySearchItem
        ): Boolean = if(oldItem is MySearchItem.SearchItem && newItem is MySearchItem.SearchItem){
            oldItem.videoUri == newItem.videoUri
        } else{
            oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: MySearchItem,
            newItem: MySearchItem
        ): Boolean = oldItem == newItem
    }
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.VideoViewHolder =
        SearchAdapter.VideoItemViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem
        )

    override fun onBindViewHolder(holder: SearchAdapter.VideoViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    abstract class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: MySearchItem)
    }

    class VideoItemViewHolder(
        private val binding: ItemSearchBinding,
        private val onClickItem: (Int, MySearchItem) -> Unit
    ) : SearchAdapter.VideoViewHolder(binding.root){
        override fun onBind(item: MySearchItem) = with(binding){
            if(item !is MySearchItem.SearchItem) {
                return@with
            }

            binding.tvVideoTitle.text=item.title
            binding.tvChannelName.text=item.uploader
            binding.tvVideoDate.text=item.uploadTime.take(10)
            binding.tvViewCount.text="%,d 회".format(item.views.toLong())
            Glide.with(binding.root).load(item.thumbnail).into(binding.imgThumbnail)
            Glide.with(binding.root).load(item.pfp).into(binding.imgPfp)

            binding.root.setOnClickListener{
                val myData = MyVideo.MyVideoItems(
                    item.videoUri,
                    item.title,
                    item.thumbnail,
                    item.content,
                    item.isLike,
                    item.views.toLong(),
                    item.tags,
                    item.uploader,
                    item.uploadTime
                )
                val bundle = Bundle().apply {
                    putParcelable("videoItem", myData)
                }
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle
                val transaction = (binding.root.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.linearLayout, detailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}
