package com.example.oic.ui.search.word

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.ext.ioScope
import com.example.oic.ui.adapter.WordItem
import com.example.oic.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    app: Application,
    private val searchWordRepository: SearchWordRepository
) : BaseViewModel(app) {

    val wordItemObservableField = ObservableField<WordItem>()

    fun searchMeanWord() {

        wordItemObservableField.get()?.let { wordItem ->
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

    }


    fun toggleBookmark(state: Boolean) {

    }

}