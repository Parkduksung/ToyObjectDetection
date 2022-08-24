package com.example.oic.ui.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.oic.base.BaseViewModel
import com.example.oic.data.repo.FirebaseRepository
import com.example.oic.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {


    /**
     * id, password, passwordOk 입력하는 값을 가져오는 변수들
     */
    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()
    val inputPasswordOkLiveData = MutableLiveData<String>()


    /**
     * 회원가입 로직.
     * 로그인 방식을 참조하여 확인할 것.
     */
    fun register() {
        ioScope {
            viewStateChanged(RegisterViewState.ShowProgress)
            viewStateChanged(RegisterViewState.EnableInput(false))
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }
            val checkPasswordOk = async { checkPasswordOk() }

            checkUser(
                checkEmail.await(),
                checkPassword.await(),
                checkPasswordOk.await()
            )?.let { person ->
                firebaseRepository.register(
                    person.email,
                    person.password
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewStateChanged(RegisterViewState.RouteHome)
                        viewStateChanged(RegisterViewState.HideProgress)
                    } else {
                        viewStateChanged(RegisterViewState.Error("회원가입을 실패하였습니다."))
                        viewStateChanged(RegisterViewState.HideProgress)
                    }
                }
            } ?: viewStateChanged(RegisterViewState.HideProgress)

            viewStateChanged(RegisterViewState.EnableInput(true))
        }
    }


    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
        checkPasswordOk: Boolean,
    ): Person? {
        return if (checkEmail && checkPassword && checkPasswordOk) {
            Person(inputEmailLiveData.value!!, inputPasswordLiveData.value!!)
        } else {
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.Error("이메일을 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.Error("비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPasswordOk(): Boolean {
        return when {
            inputPasswordLiveData.value != inputPasswordOkLiveData.value -> {
                viewStateChanged(RegisterViewState.Error("비밀번호 재입력을 올바르게 입력해 주세요."))
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