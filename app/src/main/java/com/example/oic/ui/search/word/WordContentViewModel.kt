package com.example.oic.ui.search.word

import android.app.Application
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.ext.ioScope
import com.example.oic.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordContentViewModel @Inject constructor(
    app: Application,
    private val searchWordRepository: SearchWordRepository
) : BaseViewModel(app) {
    fun searchWord(word: String) {

        ioScope {
            when (val result = searchWordRepository.getExcelList()) {

                is Result.Success -> {

                    if (result.data.isEmpty()) {
                        viewStateChanged(WordContentViewState.EmptyResult)
                    } else {
                        val toWordItemList = result.data.map { it.toWordItem() }.filter {
                            it.word.substring(0..word.length).contains(word)
                        }

                        if (toWordItemList.isEmpty()) {
                            viewStateChanged(WordContentViewState.EmptyResult)
                        } else {
                            viewStateChanged(WordContentViewState.GetSearchResult(toWordItemList))
                        }
                    }
                }

                is Result.Error -> {
                    viewStateChanged(WordContentViewState.ShowToast("단어를 찾을 수 없습니다."))
                }
            }
        }
    }

}