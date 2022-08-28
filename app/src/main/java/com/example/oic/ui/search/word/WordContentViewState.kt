package com.example.oic.ui.search.word

import com.example.oic.base.ViewState
import com.example.oic.ui.adapter.WordItem

sealed class WordContentViewState : ViewState {
    data class GetSearchResult(val list: List<WordItem>) : WordContentViewState()
    object EmptyResult : WordContentViewState()
    data class ShowToast(val message: String) : WordContentViewState()

    object ShowProgress : WordContentViewState()
    object HideProgress : WordContentViewState()
}