package com.example.bottomnavi.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.ChannelItemBinding

class ChannelAdapter(
    private val onClickItem: (Int, MyChannelItems) -> Unit,
) : ListAdapter<MyChannelItems, ChannelAdapter.ChannelViewHolder>(
    object : DiffUtil.ItemCallback<MyChannelItems>(){
        override fun areItemsTheSame(
            oldItem: MyChannelItems,
            newItem: MyChannelItems
        ): Boolean = if(oldItem is MyChannelItems && newItem is MyChannelItems){
            oldItem.thumbnail == newItem.thumbnail
        } else{
            oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: MyChannelItems,
            newItem: MyChannelItems
        ): Boolean = oldItem == newItem
    }
){
    abstract class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: MyChannelItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder =
        ChannelItemViewHolder(
            ChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem
        )

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ChannelItemViewHolder(
        private val binding: ChannelItemBinding,
        private val onClickItem: (Int, MyChannelItems) -> Unit
    ) : ChannelViewHolder(binding.root){
        override fun onBind(item: MyChannelItems) = with(binding){
            if(item !is MyChannelItems) {
                return@with
            }
            channelText.text = item.title
            Glide.with(binding.root).load(item.thumbnail).into(binding.channelImage)

        }
    }
}
