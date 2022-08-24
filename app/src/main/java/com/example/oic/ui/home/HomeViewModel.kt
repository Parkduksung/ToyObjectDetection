package com.example.oic.ui.home

import android.app.Application
import com.example.oic.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(app: Application) : BaseViewModel(app) {
}