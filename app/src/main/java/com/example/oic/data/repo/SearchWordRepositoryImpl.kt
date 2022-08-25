package com.example.oic.data.repo

import com.example.oic.api.response.DictionaryResponse
import com.example.oic.api.response.ExcelResponse
import com.example.oic.data.source.remote.SearchWordRemoteDataSource
import com.example.oic.util.Result
import javax.inject.Inject

class SearchWordRepositoryImpl @Inject constructor(private val searchWordRemoteDataSource: SearchWordRemoteDataSource) :
    SearchWordRepository {

    override suspend fun getExcelList(): Result<List<ExcelResponse>> =
        searchWordRemoteDataSource.getExcelList()

    override suspend fun searchMeanWord(word: String): Result<DictionaryResponse> =
        searchWordRemoteDataSource.searchMeanWord(word)
}