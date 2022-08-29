package com.example.oic.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentBookmarkBinding
import com.example.oic.ext.showToast
import com.example.oic.ui.adapter.BookmarkAdapter
import com.example.oic.ui.home.HomeViewModel
import com.example.oic.ui.home.HomeViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {

    private val homeViewModel by activityViewModels<HomeViewModel>()

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()

    private val bookmarkAdapter = BookmarkAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvBookmark.adapter = bookmarkAdapter

            binding.switchMean.setOnCheckedChangeListener { _, isShowMean ->
                bookmarkAdapter.toggleMean(isShowMean)
            }

        }

        bookmarkAdapter.run {
            setOnItemClickListener {
                //클릭시 대응.
            }

            itemDelete {
                homeViewModel.deleteBookmark(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bookmarkViewModel.getBookmarkList()
    }

    private fun initViewModel() {
        homeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? HomeViewState)?.let {
                onChangedHomeViewState(it)
            }
        }

        bookmarkViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? BookmarkViewState)?.let {
                onChangedBookmarkViewState(it)
            }
        }
    }

    private fun onChangedHomeViewState(viewState: HomeViewState) {
        when (viewState) {

            is HomeViewState.DeleteBookmark -> {
                bookmarkAdapter.delete(viewState.item)
            }
        }
    }

    private fun onChangedBookmarkViewState(viewState: BookmarkViewState) {
        when (viewState) {
            is BookmarkViewState.EmptyBookmarkList -> {
                binding.rvBookmark.isVisible = false
                binding.notBookmark.isVisible = true
            }

            is BookmarkViewState.ShowToast -> {
                showToast(message = viewState.message)
            }

            is BookmarkViewState.GetBookmarkList -> {
                binding.rvBookmark.isVisible = true
                binding.notBookmark.isVisible = false
                bookmarkAdapter.addAll(viewState.list)
            }
        }
    }

}