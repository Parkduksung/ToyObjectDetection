package com.example.oic.api

import com.example.oic.api.response.DictionaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    companion object {
        private const val URL_DICTIONARY = "api/v2/entries/en/{word}"
    }

    @GET(URL_DICTIONARY)
    fun getDictionaryMean(
        @Path("word") word: String
    ): Call<DictionaryResponse>

}