package com.atech.empsearch.ui.main_activity

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.empsearch.databinding.ActivityMainBinding
import com.atech.empsearch.ui.fragment.home.adapter.EmpAdapter
import com.atech.empsearch.util.addTextChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()

    private lateinit var empAdapter: EmpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            setSearchView()
            setRecyclerView()
        }
        observeData()
    }

    private fun observeData() {
        viewModel.searchResult.observe(this) {
            empAdapter.submitList(it)
            noData(it.isEmpty())
        }
    }

    private fun setRecyclerView() {
        binding.recyclerViewSearch.apply {
            adapter = EmpAdapter().also { empAdapter = it }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun noData(isTrue: Boolean) {
        binding.imageNoData.isVisible = isTrue
        binding.textNoData.isVisible = isTrue
        binding.recyclerViewSearch.isVisible = !isTrue
    }

    private fun setSearchView() {
        binding.searchView.editText.addTextChangeListener {
            viewModel.query.value = it.ifBlank { "" }
        }
    }

}