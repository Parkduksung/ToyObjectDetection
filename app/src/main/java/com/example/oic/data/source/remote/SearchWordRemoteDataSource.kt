package com.example.oic.data.source.remote

import com.example.oic.api.response.DictionaryResponse
import com.example.oic.api.response.ExcelResponse
import com.example.oic.util.Result

interface SearchWordRemoteDataSource {

    suspend fun searchMeanWord(word: String): Result<DictionaryResponse>

    suspend fun getExcelList(): Result<List<ExcelResponse>>
}