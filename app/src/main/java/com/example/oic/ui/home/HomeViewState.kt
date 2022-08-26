package com.example.oic.ui.home

import com.example.oic.base.ViewState
import com.example.oic.data.model.BookmarkWord

sealed class HomeViewState : ViewState {
    data class AddBookmark(val item: BookmarkWord) : HomeViewState()
    data class DeleteBookmark(val item: BookmarkWord) : HomeViewState()
    data class ShowToast(val message: String) : HomeViewState()
}