package com.example.oic.data.source.remote

import com.example.oic.api.DictionaryApi
import com.example.oic.api.SheetApi
import com.example.oic.api.response.DictionaryResponse
import com.example.oic.api.response.ExcelResponse
import com.example.oic.util.Result
import javax.inject.Inject

class SearchWordRemoteDataSourceImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val sheetApi: SheetApi
) :
    SearchWordRemoteDataSource {

    override suspend fun searchMeanWord(word: String): Result<DictionaryResponse> {
        return try {
            val getDictionaryResponse = dictionaryApi.getDictionaryMean(word).execute().body()!!
            Result.Success(getDictionaryResponse)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getExcelList(): Result<List<ExcelResponse>> {
        return try {
            val getExcelResponse = sheetApi.getSheetExcelData().execute().body()!!
            Result.Success(getExcelResponse)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}