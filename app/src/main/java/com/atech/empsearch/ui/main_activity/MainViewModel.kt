package com.atech.empsearch.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.empsearch.data.EmployeeRepository
import com.atech.empsearch.data.model.EmpResponseItem
import com.atech.empsearch.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {
    val empData = repository.getEmpData()

    val query = MutableStateFlow("")

    private val _searchResult: MutableLiveData<List<EmpResponseItem>> = MutableLiveData()

    val searchResult: LiveData<List<EmpResponseItem>> = _searchResult

    init {
        viewModelScope.launch {
            query.collectLatest { query ->
                val filterData = repository.getEmpData(query, filter = { q, h ->
                    val filterList = mutableListOf<EmpResponseItem>()
                    h.filter { it.username.contains(q, true) }
                        .toCollection(filterList)
                    h.filter { it.email.contains(q, true) }
                        .let {
                            it.filter { email ->
                                !filterList.any { it.email == email.email }
                            }.toCollection(filterList)
                        }
                    h.filter { it.ssn.contains(q, true) }
                        .let {
                            it.filter { ssn ->
                                !filterList.any { it.ssn == ssn.ssn }
                            }.toCollection(filterList)
                        }
                    filterList
                }).map { dataState ->
                    when (dataState) {
                        is DataState.Success ->
                            dataState.data
                        else ->
                            emptyList()
                    }
                }.toList().flatten()

                _searchResult.postValue(filterData)
            }
        }
    }
}