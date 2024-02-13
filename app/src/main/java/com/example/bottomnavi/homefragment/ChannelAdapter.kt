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
    private val onClickItem: (Int, MyChannel) -> Unit,
) : ListAdapter<MyChannel, ChannelAdapter.ChannelViewHolder>(
    object : DiffUtil.ItemCallback<MyChannel>(){
        override fun areItemsTheSame(
            oldItem: MyChannel,
            newItem: MyChannel
        ): Boolean = if(oldItem is MyChannel.MyChannelItems && newItem is MyChannel.MyChannelItems){
            oldItem.thumbnail == newItem.thumbnail
        } else{
            oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: MyChannel,
            newItem: MyChannel
        ): Boolean = oldItem == newItem
    }
){
    abstract class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: MyChannel)
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
        private val onClickItem: (Int, MyChannel) -> Unit
    ) : ChannelViewHolder(binding.root){
        override fun onBind(item: MyChannel) = with(binding){
            if(item !is MyChannel.MyChannelItems) {
                return@with
            }
            container.setOnClickListener{

            }
            channelText.text = item.channelId
            Glide.with(binding.root).load(item.thumbnail).into(binding.channelImage)

        }
    }
}
