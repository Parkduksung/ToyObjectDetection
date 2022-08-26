package com.example.oic.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentSearchBinding
import com.example.oic.ui.search.detect.DetectActivity
import com.example.oic.ui.search.word.WordActivity
import com.example.oic.util.checkPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivDetect.setOnClickListener {
            requireActivity().checkPermission { isGrant ->
                if (isGrant) {
                    startActivity(Intent(requireActivity(), DetectActivity::class.java))
                }
            }
        }
        binding.ivWord.setOnClickListener {
            startActivity(Intent(requireActivity(), WordActivity::class.java))
        }
    }
}