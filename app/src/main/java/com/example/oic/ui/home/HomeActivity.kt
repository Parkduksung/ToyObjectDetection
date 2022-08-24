package com.example.oic.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.oic.R
import com.example.oic.base.BaseActivity
import com.example.oic.databinding.ActivityHomeBinding
import com.example.oic.ui.adapter.FragmentPagerAdapter
import com.example.oic.ui.bookmark.BookmarkFragment
import com.example.oic.ui.mypage.MyPageFragment
import com.example.oic.ui.search.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private var backWait: Long = INIT_TIME

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_home)[position]
            tab.icon = resources.obtainTypedArray(R.array.array_home_icon).getDrawable(position)
        }


    private val startForResultUpdateLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()

        homeViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? HomeViewState)?.let {
                onChangedHomeViewState(it)
            }
        }

    }

    private fun onChangedHomeViewState(viewState: HomeViewState) {
        when (viewState) {

        }
    }


    private fun initUi() {

        val list = listOf(
            SearchFragment(),
            BookmarkFragment(),
            MyPageFragment(),
        )
        val pagerAdapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewpager.adapter = pagerAdapter
            viewpager.offscreenPageLimit = 5
            viewpager.isUserInputEnabled = false
            TabLayoutMediator(tab, viewpager, tabConfigurationStrategy).attach()

        }

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backWait >= LIMIT_TIME) {
            backWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val INIT_TIME = 0L
        private const val LIMIT_TIME = 2000
    }

}