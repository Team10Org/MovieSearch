package com.example.bottomnavi.shortsFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavi.databinding.ShortsItemsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class ShortsAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<ShortsItems, ShortsAdapter.ShortsViewHolder>(
    object : DiffUtil.ItemCallback<ShortsItems>(){
        override fun areItemsTheSame(
            oldItem: ShortsItems,
            newItem: ShortsItems
        ): Boolean = if(oldItem is ShortsItems && newItem is ShortsItems){
            oldItem.id == newItem.id
        } else{
            oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: ShortsItems,
            newItem: ShortsItems
        ): Boolean = oldItem == newItem
    }
){
    abstract class ShortsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        abstract fun onBind(item: ShortsItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder =
        ShortsItemViewHolder(
            ShortsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            lifecycleOwner
        )

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ShortsItemViewHolder(
        private val binding: ShortsItemsBinding,
        private val lifecycleOwner: LifecycleOwner
    ) : ShortsViewHolder(binding.root) {
        override fun onBind(item: ShortsItems) = with(binding){
            if(item !is ShortsItems) {
                return@with
            }
            val id = item.id
            val youtubePlayer = youtubePlayerShortsView
            lifecycleOwner.lifecycle.addObserver(youtubePlayer)
            youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    if (id != null) {
                        youTubePlayer.loadVideo(id, 0f)
                    }
                }
            })
        }

    }
}
