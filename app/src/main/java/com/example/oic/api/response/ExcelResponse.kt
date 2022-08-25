package com.example.oic.api.response

import com.example.oic.ui.adapter.WordItem


data class ExcelResponse(
    val word: String,
    val mean: String
) {
    fun toWordItem(): WordItem =
        WordItem(word, mean)
}