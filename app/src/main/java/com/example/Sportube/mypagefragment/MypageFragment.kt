package com.example.Sportube.mypagefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Sportube.databinding.FragmentMypageBinding
import com.example.Sportube.homefragment.HomeFragment


class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MypageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        adapter = MypageAdapter(HomeFragment.likeList)
        binding.rvMyVideos.adapter = adapter
        binding.rvMyVideos.layoutManager = LinearLayoutManager(requireContext())

        adapter.itemRemove = object : MypageAdapter.ItemRemove {
            override fun onClick(view: View, position: Int) {
                if (adapter.itemCount == 0) {
                    binding.tvNoVideos.visibility = View.VISIBLE
                } else {
                    binding.tvNoVideos.visibility = View.GONE
                }
            }
        }

        if (adapter.itemCount == 0) {
            binding.tvNoVideos.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}