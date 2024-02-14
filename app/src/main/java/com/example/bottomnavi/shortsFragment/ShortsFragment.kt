package com.example.bottomnavi.shortsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.bottomnavi.R
import com.example.bottomnavi.databinding.FragmentHomeBinding
import com.example.bottomnavi.databinding.FragmentShortsBinding
import com.example.bottomnavi.homefragment.HomeFragment.Companion.shortsList
import com.example.bottomnavi.homefragment.HomeViewModel

class ShortsFragment : Fragment() {
    private var _binding: FragmentShortsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val listAdapter: ShortsAdapter by lazy { ShortsAdapter(viewLifecycleOwner) }

    private val viewModel by lazy {
        ViewModelProvider(this)[ShortsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShortsBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    private fun initViewModel() = with(viewModel){
        searchParam.observe(viewLifecycleOwner) {
            communicateNetWork(it)
        }
        searchResult.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() = with(binding) {
        shorts.adapter = listAdapter
        shorts.layoutManager = LinearLayoutManager(context)
        viewModel.setUpShortsParameter()

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(shorts)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}