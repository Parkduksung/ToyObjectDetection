package com.example.oic.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.ext.ioScope
import com.example.oic.ext.loginUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {


    /**
     * id, password 입력하는 값을 가져오는 변수들
     */
    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()


    /**
     * 로그인 로직
     * 흐름 : 프로그래스바 보여줌 -> 입력을 false -> id,password 공란체크 -> 파이어베이스 로그인 결과 체크
     */
    fun login() {
        ioScope {
            viewStateChanged(LoginViewState.ShowProgress)
            viewStateChanged(LoginViewState.EnableInput(false))
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }

            checkUser(checkEmail.await(), checkPassword.await())?.let { person ->

                /**
                 * 파이어베이스 로그인
                 */
                firebaseRepository.loginUser(
                    person.email,
                    person.password
                ) { isLogin ->
                    if (isLogin) {
                        /**
                         * 로그인 성공시, 홈화면 전환, 프로그래스바 숨기기
                         */
                        viewStateChanged(LoginViewState.RouteHome)
                        viewStateChanged(LoginViewState.HideProgress)
                    } else {
                        /**
                         * 로그인 실패시, 실패메시지 보여주기, 프로그래스바 숨기기
                         */
                        viewStateChanged(LoginViewState.Error("로그인을 실패하였습니다."))
                        viewStateChanged(LoginViewState.HideProgress)
                    }
                }
            } ?: viewStateChanged(LoginViewState.HideProgress)

            viewStateChanged(LoginViewState.EnableInput(true))
        }
    }

    /**
     * 회원가입 화면으로 이동.
     */
    fun register() {
        viewStateChanged(LoginViewState.RouteRegister)
    }


    /**
     * 입력한 id, password 체크 여부에 따른 결과 체크
     * 성공시, id, password 를 구성하는 Person 을 반환
     * 실패시, null 을 반환.
     */
    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
    ): Person? {
        return if (checkEmail && checkPassword) {
            Person(inputEmailLiveData.value!!, inputPasswordLiveData.value!!)
        } else {
            null
        }
    }

    /**
     * 입력한 id 체크
     * 성공시 true, 실패시 false 반환.
     */
    private fun checkEmail(): Boolean {
        return when {
            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(LoginViewState.Error("이메일을 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    /**
     * 입력한 password 체크
     * 성공시 true, 실패시 false 반환.
     */
    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(LoginViewState.Error("비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    data class Person(
        val email: String,
        val password: String
    )

}