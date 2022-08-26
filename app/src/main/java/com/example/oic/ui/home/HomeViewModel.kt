package com.example.oic.ui.home

import android.app.Application
import com.example.oic.base.BaseViewModel
import com.example.oic.data.model.BookmarkWord
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.ext.addWord
import com.example.oic.ext.deleteWord
import com.example.oic.ui.bookmark.BookmarkViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    fun addBookmark(item: BookmarkWord) {
        firebaseRepository.addWord(item) { isAddBookmark ->
            if (isAddBookmark) {
                viewStateChanged(HomeViewState.AddBookmark(item))
            } else {

            }
        }
    }

    fun deleteBookmark(item: BookmarkWord) {
        firebaseRepository.deleteWord(item) { isDeleteBookmark ->
            if (isDeleteBookmark) {
                viewStateChanged(HomeViewState.DeleteBookmark(item))
            } else {

            }
        }
    }

}