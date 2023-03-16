package com.atech.empsearch.data

import com.atech.empsearch.data.model.EmpResponseItem
import com.atech.empsearch.util.DataState
import com.atech.empsearch.util.networkFetchData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val retrofitApi: RetrofitApi
) {
    fun getEmpData(
        query: String = "",
        filter: (query: String, List<EmpResponseItem>) -> List<EmpResponseItem> = { _, list ->
            list.filter {
                true
            }
        }
    ): Flow<DataState<List<EmpResponseItem>>> = networkFetchData(
        fetch = {
            retrofitApi.getEmpData()
        },
        action = {
            filter(query, it)
        }
    )
}