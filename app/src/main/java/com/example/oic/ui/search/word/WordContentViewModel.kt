package com.example.oic.ui.search.word

import android.app.Application
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.SearchWordRepository
import com.example.oic.ext.defaultScope
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
        viewStateChanged(WordContentViewState.ShowProgress)

        ioScope {
            when (val result = searchWordRepository.getExcelList()) {

                is Result.Success -> {

                    if (result.data.isEmpty()) {
                        viewStateChanged(WordContentViewState.EmptyResult)
                    } else {
                        defaultScope {
                            val toWordItemList = result.data.map { it.toWordItem() }
                                .filter {
                                    it.word.length >= word.length
                                }.filter {
                                    it.word.substring(word.indices).contains(word)
                                }

                            if (toWordItemList.isEmpty()) {
                                viewStateChanged(WordContentViewState.EmptyResult)
                            } else {
                                viewStateChanged(WordContentViewState.GetSearchResult(toWordItemList))
                            }
                        }
                    }

                    viewStateChanged(WordContentViewState.HideProgress)
                }

                is Result.Error -> {
                    viewStateChanged(WordContentViewState.ShowToast("단어를 찾을 수 없습니다."))
                    viewStateChanged(WordContentViewState.HideProgress)
                }
            }
        }
    }

}