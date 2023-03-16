package com.atech.empsearch.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.empsearch.R
import com.atech.empsearch.databinding.FragmentHomeBinding
import com.atech.empsearch.ui.fragment.home.adapter.EmpAdapter
import com.atech.empsearch.ui.main_activity.MainViewModel
import com.atech.empsearch.util.DataState
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var empAdapter: EmpAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.apply {
                adapter = EmpAdapter().also { empAdapter = it }
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launchWhenStarted {
            viewModel.empData.collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        Log.d(TAG, "collectData: ${dataState.exception.message}")
                    }

                    DataState.Loading -> {

                    }

                    is DataState.Success ->
                        empAdapter.submitList(dataState.data)
                }
            }
        }
    }
}