package com.example.bottomnavi.mypagefragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavi.DetailFragment
import com.example.bottomnavi.R
import com.example.bottomnavi.databinding.FragmentMypageItemRecyclerviewBinding
import com.example.bottomnavi.homefragment.HomeFragment.Companion.likeList
import com.example.bottomnavi.homefragment.MyVideo
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MypageAdapter(private var mItems: MutableList<MyVideo.MyVideoItems>) :
    RecyclerView.Adapter<MypageAdapter.MyVideoViewHolder>() {

    interface ItemRemove {
        fun onClick(view: View, position: Int)
    }

    var itemRemove: ItemRemove? = null

    inner class MyVideoViewHolder(binding: FragmentMypageItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val thumbnails = binding.ivThumbnail
        val views = binding.tvViews
        val items = binding.clRvItem
        val uploader = binding.tvUploader
        val datetime = binding.tvDatetime
        val like = binding.ivLike
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        Glide.with(holder.itemView.context)
            .load(mItems[position].thumbnail)
            .into(holder.thumbnails)
        // 조회수 포맷
        holder.views.text = "%,d".format(mItems[position].views?.toLong()) + "회"
        holder.uploader.text = mItems[position].channelTitle
        // 게시일 포맷
        val parsed = OffsetDateTime.parse(mItems[position].publishedAt)
        val formatter = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        holder.datetime.text = formatter

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
                mItems[position].publishedAt,
                mItems[position].channelImage,
            )
            Log.d("checking", "isLike : ${mItems[position].isLike}")
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

        holder.like.setOnClickListener {
            likeList.remove(
                MyVideo.MyVideoItems(
                    mItems[position].videoUri,
                    mItems[position].title,
                    mItems[position].thumbnail,
                    mItems[position].content,
                    mItems[position].isLike,
                    mItems[position].views,
                    mItems[position].tags,
                    mItems[position].channelTitle,
                    mItems[position].publishedAt,
                    mItems[position].channelImage,
                )
            )
            notifyDataSetChanged()
            itemRemove?.onClick(it, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

}