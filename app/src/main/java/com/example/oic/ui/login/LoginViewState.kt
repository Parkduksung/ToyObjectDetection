package com.example.oic.ui.login


import com.example.oic.base.ViewState

/**
 * LoginActivity  에 화면상태 변화를 나타내는 클래스
 */
sealed class LoginViewState : ViewState {
    object RouteRegister : LoginViewState()
    object RouteHome : LoginViewState()
    data class Error(val message: String) : LoginViewState()
    data class EnableInput(val isEnable: Boolean) : LoginViewState()
    object ShowProgress : LoginViewState()
    object HideProgress : LoginViewState()
}
