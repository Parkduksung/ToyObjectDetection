package com.example.oic.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.oic.R
import com.example.oic.base.BaseActivity
import com.example.oic.databinding.ActivitySplashBinding
import com.example.oic.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {

        /**
         * 애니메이션 관련 리스너
         */
        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            /**
             * 애니메이션 cycle 이 끝났을때 나타내는 콜백 함수.
             */
            override fun onAnimationEnd(animation: Animator) {
                /**
                 * LoginActivity 화면 전환
                 */
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}