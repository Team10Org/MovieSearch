package com.example.bottomnavi.homefragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavi.R
import com.example.bottomnavi.databinding.FragmentHomeBinding
import com.example.bottomnavi.shortsFragment.ShortsItems
import java.nio.channels.Channel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var selectedTag: String? = null

    companion object {
        var videoList: ArrayList<MyVideo.MyVideoItems> = ArrayList()
        var channelList = mutableListOf<MyChannelItems>()
        var likeList = mutableListOf<MyVideo.MyVideoItems>()
        var shortsList = mutableListOf<ShortsItems>()
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val listAdapter: VideoAdapter by lazy {
        VideoAdapter(
            onClickItem = { position, item ->

            }
        )
    }
    private val channelListAdapter: ChannelAdapter by lazy {
        ChannelAdapter(
            onClickItem = { position, item ->

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    private fun initViewModel() = with(viewModel) {
        searchParam.observe(viewLifecycleOwner) {
            communicateNetWork(it)
        }
        searchResult.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
        searchChannelParam.observe(viewLifecycleOwner){
            communicateChannelNetWork(it)
        }
        searchChannelResult.observe(viewLifecycleOwner){
            channelListAdapter.submitList(it)
        }
        searchChannelResult.observe(viewLifecycleOwner){
            channelListAdapter.submitList(it)
        }
        filterVideo.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun initView() {
        binding.channelNameRecycler.adapter = channelListAdapter
        binding.channelNameRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.videoRecycler.adapter = listAdapter
        binding.videoRecycler.layoutManager = GridLayoutManager(context, 2)
        spinnerSetting()
        viewModel.setUpVideoParameter()
    }


    private fun spinnerSetting() {
        val items = resources.getStringArray(R.array.tag_array)
        val spinnerAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.custom_spinner_item,
            R.id.spinner_text,
            items
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(R.id.spinner_text)
                textView.gravity = Gravity.CENTER
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(R.id.spinner_text)
                val image = view.findViewById<ImageView>(R.id.spinner_arrow)
                image.visibility = INVISIBLE
                textView.gravity = Gravity.CENTER
                return view
            }
        }
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTag = items[position]
                viewModel.filterVideoList(selectedTag)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}