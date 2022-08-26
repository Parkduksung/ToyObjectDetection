package com.example.oic.ui.search.word

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.example.oic.base.BaseViewModel
import com.example.oic.data.model.BookmarkWord
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.ext.*
import com.example.oic.ui.adapter.WordItem
import com.example.oic.util.Result
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    app: Application,
    private val searchWordRepository: SearchWordRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    val wordItemObservableField = ObservableField<WordItem>()

    fun searchMeanWord() {
        wordItemObservableField.get()?.let { wordItem ->
            checkBookmark()

            ioScope {
                when (val result = searchWordRepository.searchMeanWord(wordItem.word)) {
                    is Result.Success -> {
                        Log.d("결과", result.data.toString())
                    }

                    is Result.Error -> {
                        viewStateChanged(WordDetailViewState.ShowToast("단어를 찾을 수 없습니다."))
                    }
                }
            }
        }
    }

    private fun checkBookmark() {
        firebaseRepository.getWordList { bookmarkList ->
            bookmarkList?.let {
                val filterList =
                    it.filter {
                        (it.word == wordItemObservableField.get()!!.word) &&
                                (it.mean == wordItemObservableField.get()!!.mean)
                    }
                viewStateChanged(WordDetailViewState.BookmarkState(filterList.isNotEmpty()))
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
                        viewStateChanged(WordDetailViewState.ShowToast("즐겨찾기 추가를 실패하였습니다."))
                    }
                }
            }
        } else {
            ioScope {
                firebaseRepository.deleteWord(
                    wordItemObservableField.get()!!.toBookmarkWord()
                ) { isDeleteBookmark ->
                    if (!isDeleteBookmark) {
                        viewStateChanged(WordDetailViewState.ShowToast("즐겨찾기 제거를 실패하였습니다."))
                    }
                }
            }
        }
    }

}