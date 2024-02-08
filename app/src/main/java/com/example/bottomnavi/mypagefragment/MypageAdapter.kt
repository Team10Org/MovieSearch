package com.example.bottomnavi.mypagefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.databinding.FragmentMypageItemRecyclerviewBinding
import com.example.bottomnavi.homefragment.HomeFragment
import com.example.bottomnavi.homefragment.MyVideo

class MypageAdapter(private var mItems: MutableList<MyVideo.MyVideoItems>) :
    RecyclerView.Adapter<MypageAdapter.MyVideoViewHolder>() {

    inner class MyVideoViewHolder(binding: FragmentMypageItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val thumbnails = binding.ivThumbnail
        val views = binding.tvViews
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyVideoViewHolder {
        val binding = FragmentMypageItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        Glide.with(holder.itemView.context)
            .load(mItems[position].thumbnail)
            .into(holder.thumbnails)
        holder.views.text = mItems[position].views.toString()
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

}