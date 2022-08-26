package com.example.oic.api

import com.example.oic.api.response.ExcelResponse
import retrofit2.Call
import retrofit2.http.GET

interface SheetApi {

    companion object {
        private const val URL = "api/v1/hpx4embn9i9tj"

    }

    @GET(URL)
    fun getSheetExcelData(): Call<List<ExcelResponse>>
}
