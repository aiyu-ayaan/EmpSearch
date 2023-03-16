package com.atech.empsearch.data

import com.atech.empsearch.data.model.EmpResponseItem
import retrofit2.http.GET

interface RetrofitApi {
    companion object {
        const val BASE_URL = "https://aiyu-ayaan.github.io/Dummy-JSON/"
    }

    @GET("emp.json")
    suspend fun getEmpData(): List<EmpResponseItem>

}