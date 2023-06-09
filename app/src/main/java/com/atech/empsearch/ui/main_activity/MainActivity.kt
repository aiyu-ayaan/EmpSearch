package com.atech.empsearch.ui.main_activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.viewbinding.library.activity.viewBinding
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.empsearch.R
import com.atech.empsearch.databinding.ActivityMainBinding
import com.atech.empsearch.ui.fragment.home.adapter.EmpAdapter
import com.atech.empsearch.util.QueryType
import com.atech.empsearch.util.addTextChangeListener
import com.google.android.material.color.MaterialColors
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
            setChip()
            searchBarState()
        }
        observeData()
        onBackPressedDispatcher.addCallback {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                finish()
            }
        }
    }

    private fun searchBarState() {
        binding.searchView.addTransitionListener { _, _, newState ->
            if (newState == SearchView.TransitionState.HIDDEN) {
                binding.searchBarLayout.chipGroup.clearCheck()
                viewModel.query.value = QueryType.ALL.value
                binding.searchBarLayout.chipAll.isChecked = true
            }
        }
    }

    private fun setChip() {
        binding.searchBarLayout.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            checkedIds.forEach { id ->
                when (id) {
                    R.id.chipAddress -> viewModel.query.value = QueryType.ADDRESS.value
                    R.id.chipDep -> viewModel.query.value = QueryType.DEP.value
                    R.id.chipEmail -> viewModel.query.value = QueryType.EMAIL.value
                    R.id.chipName -> viewModel.query.value = QueryType.NAME.value
                    R.id.chipPhone -> viewModel.query.value = QueryType.PHONE.value
                    R.id.chipSsn -> viewModel.query.value = QueryType.SSN.value
                    else -> viewModel.query.value = QueryType.ALL.value
                }
                setSearchEditTextValue(viewModel.query.value)
            }
        }
    }

    private fun observeData() {
        viewModel.searchResult.observe(this) {
            empAdapter.submitList(it)
            noData(it.isEmpty())
        }
    }

    private fun setRecyclerView() {
        binding.searchBarLayout.recyclerViewSearch.apply {
            adapter = EmpAdapter().also { empAdapter = it }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun noData(isTrue: Boolean) {
        binding.searchBarLayout.imageNoData.isVisible = isTrue
        binding.searchBarLayout.textNoData.isVisible = isTrue
        binding.searchBarLayout.recyclerViewSearch.isVisible = !isTrue
    }

    private fun setSearchView() {
        binding.searchView.editText.addTextChangeListener {
            viewModel.query.value = it.ifBlank {
                QueryType.ALL.value
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.query.collectLatest {
                when (it) {
                    QueryType.ADDRESS.value -> binding.setChipSelected(R.id.chipAddress)
                    QueryType.DEP.value -> binding.setChipSelected(R.id.chipDep)
                    QueryType.EMAIL.value -> binding.setChipSelected(R.id.chipEmail)
                    QueryType.NAME.value -> binding.setChipSelected(R.id.chipName)
                    QueryType.PHONE.value -> binding.setChipSelected(R.id.chipPhone)
                    QueryType.SSN.value -> binding.setChipSelected(R.id.chipSsn)
                    QueryType.ALL.value -> binding.setChipSelected(R.id.chipAll)
                }
            }
        }
    }

    private fun setSearchEditTextValue(value: String) = binding.searchView.editText.apply {
        val spannableString = SpannableString(value)
        spannableString.setSpan(
            ForegroundColorSpan(
                MaterialColors.getColor(
                    this, androidx.appcompat.R.attr.colorPrimary, Color.CYAN
                )
            ), 0, value.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setText(spannableString)
        setSelection(binding.searchView.editText.text.length)
    }

    private fun ActivityMainBinding.setChipSelected(@IdRes id: Int) = this.searchBarLayout.apply {
        listOf(
            chipAddress,
            chipDep,
            chipEmail,
            chipName,
            chipPhone,
            chipSsn,
            chipAll
        ).associateWith { it.id }.forEach { (button, buttonId) ->
            button.isChecked = buttonId == id
        }
    }
}