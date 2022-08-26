package com.example.oic.ui.bookmark

import android.app.Application
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.ext.getWordList
import com.example.oic.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {


    fun getBookmarkList() {
        ioScope {
            firebaseRepository.getWordList { list ->
                if (!list.isNullOrEmpty()) {
                    viewStateChanged(BookmarkViewState.GetBookmarkList(list))
                } else {
                    viewStateChanged(BookmarkViewState.EmptyBookmarkList)
                }
            }
        }
    }
}