package com.example.oic.ui.search.detect

import android.app.Application
import androidx.databinding.ObservableField
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.ext.addWord
import com.example.oic.ext.deleteWord
import com.example.oic.ext.getWordList
import com.example.oic.ext.ioScope
import com.example.oic.ui.adapter.WordItem
import com.example.oic.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectDetectionViewModel @Inject constructor(
    app: Application,
    private val searchWordRepository: SearchWordRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    private val wordItemObservableField = ObservableField<WordItem>()

    fun searchMeanWord(word: String?) {
        word?.let {
            viewStateChanged(SelectDetectionViewState.ShowProgress)

            ioScope {
                when (val result = searchWordRepository.getExcelList()) {

                    is Result.Success -> {

                        val getData = result.data.filter { it.word == word }

                        if (getData.isNotEmpty()) {
                            when (val result =
                                searchWordRepository.searchMeanWord(getData[0].word)) {
                                is Result.Success -> {
                                    if (result.data.isNotEmpty()) {
                                        wordItemObservableField.set(
                                            getData[0].toWordItem()
                                        )
                                        viewStateChanged(
                                            SelectDetectionViewState.GetSearchWord(
                                                result.data[0]
                                            )
                                        )
                                    } else {
                                        viewStateChanged(SelectDetectionViewState.NotSearchWord)
                                        viewStateChanged(SelectDetectionViewState.ShowToast("단어를 찾을 수 없습니다."))
                                    }
                                }
                                is Result.Error -> {
                                    viewStateChanged(SelectDetectionViewState.NotSearchWord)
                                    viewStateChanged(SelectDetectionViewState.ShowToast("단어를 찾을 수 없습니다."))
                                }
                            }

                        } else {
                            viewStateChanged(SelectDetectionViewState.NotSearchWord)
                            viewStateChanged(SelectDetectionViewState.ShowToast("단어를 찾을 수 없습니다."))
                        }

                    }

                    is Result.Error -> {
                        viewStateChanged(SelectDetectionViewState.NotSearchWord)
                        viewStateChanged(SelectDetectionViewState.ShowToast("단어를 찾을 수 없습니다."))
                    }

                }
            }

            viewStateChanged(SelectDetectionViewState.HideProgress)
        }
    }

    fun checkBookmark() {
        firebaseRepository.getWordList { bookmarkList ->
            if (bookmarkList != null) {
                val filterList =
                    bookmarkList.filter {
                        (it.word == wordItemObservableField.get()!!.word) && (it.mean == wordItemObservableField.get()!!.mean)
                    }
                viewStateChanged(SelectDetectionViewState.BookmarkState(filterList.isNotEmpty()))
            } else {
                viewStateChanged(SelectDetectionViewState.BookmarkState(false))
            }
        }
    }


    fun toggleBookmark(state: Boolean) {
        if (state) {
            ioScope {
                firebaseRepository.addWord(
                    wordItemObservableField.get()!!.toBookmarkWord()
                ) { isAddBookmark ->
                    if (!isAddBookmark) {
                        viewStateChanged(SelectDetectionViewState.ShowToast("즐겨찾기 추가를 실패하였습니다."))
                    }
                }
            }
        } else {
            ioScope {
                firebaseRepository.deleteWord(
                    wordItemObservableField.get()!!.toBookmarkWord()
                ) { isDeleteBookmark ->
                    if (!isDeleteBookmark) {
                        viewStateChanged(SelectDetectionViewState.ShowToast("즐겨찾기 제거를 실패하였습니다."))
                    }
                }
            }
        }
    }

}