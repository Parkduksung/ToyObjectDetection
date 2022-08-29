package com.example.oic.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentMyPageBinding
import com.example.oic.ui.bookmark.BookmarkViewState
import com.example.oic.ui.dialog.ChooseDialog
import com.example.oic.ui.dialog.ChooseItem
import com.example.oic.ui.dialog.WithdrawDialog
import com.example.oic.ui.home.HomeActivity
import com.example.oic.ui.home.HomeViewModel
import com.example.oic.ui.home.HomeViewState
import com.example.oic.ui.login.LoginActivity
import com.example.oic.util.EventDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {


    private val myPageViewModel by viewModels<MyPageViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {

    }

    private fun initViewModel() {

        binding.viewModel = myPageViewModel

        myPageViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? MyPageViewState)?.let {
                onChangedMyPageViewState(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myPageViewModel.getBookmarkList()
    }

    private fun onChangedMyPageViewState(viewState: MyPageViewState) {
        when (viewState) {
            is MyPageViewState.GetBookmarkList -> {
                viewState.list
            }

            is MyPageViewState.GetCalendarList -> {
                viewState.list.forEach {
                    binding.calendarView.addDecorator(
                        EventDecorator(
                            listOf(it.first),
                            requireActivity(),
                            it.second.convertLevel()
                        )
                    )
                }
            }

            is MyPageViewState.ShowLogoutDialog -> {
                ChooseDialog(
                    ChooseItem(
                        title = "로그아웃",
                        content = "로그아웃 하시겠습니까?",
                        negativeString = "취소",
                        positiveString = "로그아웃"
                    ),
                    dismissCallback = {
                        myPageViewModel.logout()
                    }
                ).show(childFragmentManager, "ChooseDialog")
            }

            is MyPageViewState.ShowWithdrawDialog -> {
                ChooseDialog(
                    ChooseItem(
                        title = "회원탈퇴",
                        content = "회원탈퇴 하시겠습니까?",
                        negativeString = "취소",
                        positiveString = "탈퇴"
                    ),
                    dismissCallback = {
                        WithdrawDialog(
                            ChooseItem(
                                title = "회원탈퇴",
                                content = "",
                                negativeString = "취소",
                                positiveString = "탈퇴"
                            ),
                            dismissCallback = {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        LoginActivity::class.java
                                    ).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    })
                            }
                        ).show(childFragmentManager, "WithdrawDialog")
                    }
                ).show(childFragmentManager, "ChooseDialog")
            }

            is MyPageViewState.Logout -> {
                startActivity(Intent(requireActivity(), LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }

            is MyPageViewState.ShowProgress -> {
                binding.progressbar.bringToFront()
                binding.progressbar.isVisible = true
            }

            is MyPageViewState.HideProgress -> {
                binding.progressbar.isVisible = false
            }
        }
    }

    private fun Int.convertLevel(): String {
        return when (this) {
            in 1..3 -> {
                "level_1"
            }

            in 4..6 -> {
                "level_2"
            }

            else -> {
                "level_3"
            }
        }
    }
}