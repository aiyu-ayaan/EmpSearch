package com.atech.empsearch.ui.fragment.home

import androidx.lifecycle.ViewModel
import com.atech.empsearch.data.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {
    val empData = repository.getEmpData()

}