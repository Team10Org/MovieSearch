package com.example.bottomnavi.homefragment

enum class VideoListViewType{
    ITEM,
    UNKNOWN
    ;
    companion object{
        fun from(ordinal:Int): VideoListViewType = VideoListViewType.values().find{
            it.ordinal == ordinal
        } ?: UNKNOWN
    }
}