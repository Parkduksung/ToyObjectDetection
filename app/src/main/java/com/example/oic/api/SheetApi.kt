package com.example.oic.api

import com.example.oic.api.response.ExcelResponse
import retrofit2.Call
import retrofit2.http.GET

interface SheetApi {

    companion object {
        private const val URL = "api/v1/3esrcl2hh030u"

    }

    @GET(URL)
    fun getSheetExcelData(): Call<List<ExcelResponse>>
}
