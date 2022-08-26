package com.example.oic.ui.bookmark

import com.example.oic.base.ViewState
import com.example.oic.data.model.BookmarkWord

sealed class BookmarkViewState : ViewState {
    data class GetBookmarkList(val list: List<BookmarkWord>) : BookmarkViewState()
    object EmptyBookmarkList : BookmarkViewState()
    data class ShowToast(val message: String) : BookmarkViewState()
}