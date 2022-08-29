package com.example.oic.ui.search.detect

import com.example.oic.api.response.DictionaryResponseItem
import com.example.oic.base.ViewState

sealed class SelectDetectionViewState : ViewState {
    data class GetSearchWord(val word: DictionaryResponseItem) : SelectDetectionViewState()
    data class BookmarkState(val isBookmark: Boolean) : SelectDetectionViewState()
    data class ShowToast(val message: String) : SelectDetectionViewState()

    object NotSearchWord : SelectDetectionViewState()

    object ShowProgress : SelectDetectionViewState()
    object HideProgress : SelectDetectionViewState()
}