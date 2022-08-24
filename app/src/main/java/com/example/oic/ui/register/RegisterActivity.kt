package com.example.oic.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.oic.R
import com.example.oic.base.BaseActivity
import com.example.oic.databinding.ActivityRegisterBinding
import com.example.oic.ext.showToast
import com.example.oic.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {

    private val registerViewModel by viewModels<RegisterViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.inputPassRegisterOk.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register()
                true
            } else {
                false
            }
        }
    }

    /**
     * 뷰모델 초기화
     */
    private fun initViewModel() {
        binding.viewModel = registerViewModel
        registerViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? RegisterViewState)?.let {
                onChangedRegisterViewState(
                    viewState
                )
            }
        }
    }

    /**
     * 상태에 따른 화면변화를 나타냄
     */
    private fun onChangedRegisterViewState(viewState: RegisterViewState) {
        when (viewState) {

            /**
             * 회원가입 실패시 에러 메세지 보여줌.
             */
            is RegisterViewState.Error -> {
                showToast(message = viewState.message)
            }

            /**
             * Id, Password, PasswordOk 입력 on/off
             */
            is RegisterViewState.EnableInput -> {
                with(binding) {
                    inputEmailRegister.isEnabled = viewState.isEnable
                    inputPassRegisterOk.isEnabled = viewState.isEnable
                    inputPassRegister.isEnabled = viewState.isEnable
                }
            }

            /**
             * HomeActivity 로 화면 전환
             */
            is RegisterViewState.RouteHome -> {
                startActivity(Intent(this@RegisterActivity, HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }

            /**
             * Progress Show
             */
            is RegisterViewState.ShowProgress -> {
                binding.progressbar.bringToFront()
                binding.progressbar.isVisible = true
            }

            /**
             * Progress Hide
             */
            is RegisterViewState.HideProgress -> {
                binding.progressbar.isVisible = false
            }
        }
    }
}