package com.example.oic.ui.register

import com.example.oic.base.ViewState

/**
 * RegisterActivity  에 화면상태 변화를 나타내는 클래스
 */
sealed class RegisterViewState  : ViewState {
    object RouteHome : RegisterViewState()
    data class Error(val message: String) : RegisterViewState()
    data class EnableInput(val isEnable: Boolean) : RegisterViewState()
    object ShowProgress : RegisterViewState()
    object HideProgress : RegisterViewState()
}