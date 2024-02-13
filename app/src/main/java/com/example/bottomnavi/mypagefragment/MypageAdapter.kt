package com.example.bottomnavi.mypagefragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.DetailFragment
import com.example.bottomnavi.R
import com.example.bottomnavi.databinding.FragmentMypageItemRecyclerviewBinding
import com.example.bottomnavi.homefragment.MyVideo

class MypageAdapter(private var mItems: MutableList<MyVideo.MyVideoItems>) :
    RecyclerView.Adapter<MypageAdapter.MyVideoViewHolder>() {

    inner class MyVideoViewHolder(binding: FragmentMypageItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val thumbnails = binding.ivThumbnail
        val views = binding.tvViews
        val items = binding.clRvItem
        val uploader = binding.tvUploader
        val datetime = binding.tvDatetime
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyVideoViewHolder {
        val binding = FragmentMypageItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        Glide.with(holder.itemView.context)
            .load(mItems[position].thumbnail)
            .into(holder.thumbnails)
        holder.views.text = mItems[position].views.toString()
        holder.uploader.text = mItems[position].channelTitle
        holder.datetime.text = mItems[position].publishedAt

        holder.items.setOnClickListener {
            val myData = MyVideo.MyVideoItems(
                mItems[position].videoUri,
                mItems[position].title,
                mItems[position].thumbnail,
                mItems[position].content,
                mItems[position].isLike,
                mItems[position].views,
                mItems[position].tags,
                mItems[position].channelTitle,
                mItems[position].publishedAt
            )
            Log.d("checking","isLike : ${mItems[position].isLike}")
            val bundle = Bundle().apply {
                putParcelable("videoItem", myData)
            }
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            (holder.items.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.linearLayout, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

}