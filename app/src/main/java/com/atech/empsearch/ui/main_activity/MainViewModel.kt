package com.atech.empsearch.ui.main_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.empsearch.data.EmployeeRepository
import com.atech.empsearch.data.model.EmpResponseItem
import com.atech.empsearch.util.DataState
import com.atech.empsearch.util.QueryType
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

    val query = MutableStateFlow(QueryType.ALL.value)

    private val _searchResult: MutableLiveData<List<EmpResponseItem>> = MutableLiveData()

    val searchResult: LiveData<List<EmpResponseItem>> = _searchResult

    init {
        viewModelScope.launch {
            query.collectLatest { query ->
                val filterData = repository.getEmpData(query, filter = { q, h ->
                    val filterList = mutableListOf<EmpResponseItem>()
                    if (query.contains(QueryType.DEP.value)) {
                        h.filter {
                            it.department.contains(
                                q.replace(QueryType.DEP.value, ""),
                                true
                            )
                        }.toCollection(filterList)

                        h.filter {
                            it.shortDepartment.contains(
                                q.replace(QueryType.DEP.value, ""),
                                true
                            )
                        }.let {
                            it.filter { item -> !filterList.contains(item) }
                                .toCollection(filterList)
                        }
                    }
                    if (query.contains(QueryType.EMAIL.value))
                        h.filter { it.email.contains(q.replace(QueryType.EMAIL.value, ""), true) }
                            .toCollection(filterList)
                    if (query.contains(QueryType.SSN.value))
                        h.filter { it.ssn.contains(q.replace(QueryType.SSN.value, ""), true) }
                            .toCollection(filterList)
                    if (query.contains(QueryType.NAME.value))
                        h.filter { it.fullName.contains(q.replace(QueryType.NAME.value, ""), true) }
                            .toCollection(filterList)
                    if (query.contains(QueryType.ADDRESS.value))
                        h.filter {
                            it.fullAddress.contains(
                                q.replace(QueryType.ADDRESS.value, ""),
                                true
                            )
                        }
                            .toCollection(filterList)
                    if (query.contains(QueryType.PHONE.value))
                        h.filter {
                            it.fullPhone.contains(
                                q.replace(QueryType.PHONE.value, ""),
                                true
                            )
                        }
                            .toCollection(filterList)
                    else {
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
                        h.filter {
                            it.fullName.contains(q, true)
                        }
                            .let {
                                it.filter { first ->
                                    !filterList.any { it.fullName == first.fullName }
                                }.toCollection(filterList)
                            }
                        h.filter { it.fullAddress.contains(q, true) }
                            .let {
                                it.filter { address ->
                                    !filterList.any { it.fullAddress == address.fullAddress }
                                }.toCollection(filterList)
                            }
                        h.filter { it.fullPhone.contains(q, true) }
                            .let {
                                it.filter { phone ->
                                    !filterList.any { it.fullPhone == phone.fullPhone }
                                }.toCollection(filterList)
                            }
                        h.filter { it.shortDepartment.contains(q, true) }
                            .let {
                                it.filter { dep ->
                                    !filterList.any { it.shortDepartment == dep.shortDepartment }
                                }.toCollection(filterList)
                            }
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