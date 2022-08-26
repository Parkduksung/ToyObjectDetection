package com.example.oic.ui.search.word

import com.example.oic.base.ViewState

sealed class WordDetailViewState : ViewState {
    data class GetSearchWord(val list: List<String>) : WordDetailViewState()
    data class BookmarkState(val isBookmark: Boolean) : WordDetailViewState()
    data class ShowToast(val message: String) : WordDetailViewState()
}