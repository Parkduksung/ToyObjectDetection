package com.example.oic.ui.search.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentWordDetailBinding
import com.example.oic.ext.showToast
import com.example.oic.ui.adapter.WordItem
import dagger.hilt.android.AndroidEntryPoint

const val ARG_WORD = "item_word"

@AndroidEntryPoint
class WordDetailFragment : BaseFragment<FragmentWordDetailBinding>(R.layout.fragment_word_detail) {
    private var wordItem: WordItem? = null

    private val wordDetailViewModel by viewModels<WordDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordItem = it.getParcelable(ARG_WORD)
        }
        wordDetailViewModel.wordItemObservableField.set(wordItem)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initViewModel() {

        wordDetailViewModel.searchMeanWord()

        wordDetailViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? WordDetailViewState)?.let {
                onChangedWordDetailViewState(it)
            }
        }

    }

    private fun onChangedWordDetailViewState(viewState: WordDetailViewState) {
        when (viewState) {

            is WordDetailViewState.BookmarkState -> {

            }

            is WordDetailViewState.GetSearchWord -> {

            }

            is WordDetailViewState.ShowToast -> {
                showToast(message = viewState.message)
            }

        }
    }
}