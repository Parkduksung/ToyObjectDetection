package com.example.oic.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.oic.R
import com.example.oic.base.BaseActivity
import com.example.oic.databinding.ActivityLoginBinding
import com.example.oic.ext.showToast
import com.example.oic.ui.home.HomeActivity
import com.example.oic.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
    }

    private fun initUi() {

        /**
         * password 입력하고 키보드창에서 완료 누를시 로그인이 자동 되게끔 구현.
         */
        binding.inputPassLogin.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login()
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
        binding.viewModel = loginViewModel
        loginViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? LoginViewState)?.let {
                onChangedLoginViewState(
                    viewState
                )
            }
        }
    }

    /**
     * 상태에 따른 화면변화를 나타냄
     */
    private fun onChangedLoginViewState(viewState: LoginViewState) {
        when (viewState) {

            /**
             * 로그인 실패시 에러 메세지 보여줌.
             */
            is LoginViewState.Error -> {
                showToast(message = viewState.message)
            }

            /**
             * Id, Password 입력 on/off
             */
            is LoginViewState.EnableInput -> {
                with(binding) {
                    inputEmailLogin.isEnabled = viewState.isEnable
                    inputPassLogin.isEnabled = viewState.isEnable
                }
            }

            /**
             * HomeActivity 로 화면 전환
             */
            is LoginViewState.RouteHome -> {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }

            /**
             * RegisterActivity 로 화면 전환
             */
            is LoginViewState.RouteRegister -> {
                with(binding) {
                    inputEmailLogin.text.clear()
                    inputPassLogin.text.clear()
                }
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            /**
             * Progress Show
             */
            is LoginViewState.ShowProgress -> {
                binding.progressbar.bringToFront()
                binding.progressbar.isVisible = true
            }

            /**
             * Progress Hide
             */
            is LoginViewState.HideProgress -> {
                binding.progressbar.isVisible = false
            }
        }
    }
}