package com.example.Sportube.shortsFragment

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.Sportube.databinding.ShortsItemsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
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
            lifecycleOwner,
            this
        )

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ShortsItemViewHolder(
        private val binding: ShortsItemsBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val adapter: ShortsAdapter
    ) : ShortsViewHolder(binding.root) {
        override fun onBind(item: ShortsItems) = with(binding){
            if(item !is ShortsItems) {
                return@with
            }
            val id = item.id
            val youtubePlayer = youtubePlayerShortsView
            lifecycleOwner.lifecycle.addObserver(youtubePlayer)

            val displayMetrics = DisplayMetrics()
            (binding.root.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
            val layoutParams = youtubePlayer.layoutParams
            layoutParams.width = displayMetrics.widthPixels
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            youtubePlayer.layoutParams = layoutParams

            youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    if (id != null) {
                        youTubePlayer.loadVideo(id, 0f)
                    }
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    super.onStateChange(youTubePlayer, state)
                    if(state == PlayerConstants.PlayerState.ENDED){
                        val currentPosition = adapterPosition
                        if (currentPosition != RecyclerView.NO_POSITION && currentPosition + 1 < adapter.itemCount) {
                            // 다음 아이템이 있으면 다음 아이템으로 이동
                            val nextItem = adapter.getItem(currentPosition + 1)
                            nextItem.id?.let { youTubePlayer.loadVideo(it, 0f) }
                        }
                    }
                }
            })
        }

    }
}
