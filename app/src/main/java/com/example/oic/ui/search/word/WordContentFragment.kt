package com.example.oic.ui.search.word

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentWordContentBinding
import com.example.oic.ext.showToast
import com.example.oic.ext.textChanges
import com.example.oic.ui.adapter.WordAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WordContentFragment :
    BaseFragment<FragmentWordContentBinding>(R.layout.fragment_word_content) {

    private val wordContentViewModel by viewModels<WordContentViewModel>()

    private val wordAdapter = WordAdapter()

    private var isStopAndDestroy = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            etSearch.textChanges().debounce(SEARCH_DEBOUNCE_TIME).onEach { searchText ->
                if (!searchText.isNullOrEmpty()) {
                    if (!isStopAndDestroy) {
                        wordContentViewModel.searchWord(searchText.toString())
                    }
                }
            }.launchIn(MainScope())

            rvWord.adapter = wordAdapter
        }

        wordAdapter.setOnItemClickListener { clickItem ->
            findNavController().navigate(
                R.id.action_content_to_detail,
                bundleOf(ARG_WORD to clickItem)
            )
        }
    }

    private fun initViewModel() {
        wordContentViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? WordContentViewState)?.let {
                onChangedWordContentViewState(it)
            }
        }

    }

    private fun onChangedWordContentViewState(viewState: WordContentViewState) {
        when (viewState) {

            is WordContentViewState.EmptyResult -> {
                binding.rvWord.isVisible = false
                binding.tvNotResult.isVisible = true
            }

            is WordContentViewState.GetSearchResult -> {
                binding.rvWord.isVisible = true
                binding.tvNotResult.isVisible = false
                wordAdapter.addAll(viewState.list)
            }

            is WordContentViewState.ShowToast -> {
                showToast(message = viewState.message)
            }
        }
    }

    override fun onStop() {
        isStopAndDestroy = true
        super.onStop()
    }

    override fun onDestroy() {
        isStopAndDestroy = true
        super.onDestroy()
    }


    companion object {
        private const val SEARCH_DEBOUNCE_TIME = 1500L
    }
}
