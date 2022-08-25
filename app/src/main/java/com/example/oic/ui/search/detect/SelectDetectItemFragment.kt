package com.example.oic.ui.search.detect

import android.os.Bundle
import android.view.View
import com.example.oic.R
import com.example.oic.base.BaseFragment
import com.example.oic.databinding.FragmentSelectDetectItemBinding
import dagger.hilt.android.AndroidEntryPoint


const val ARG_SELECT_ITEM = "item_select_detection"

@AndroidEntryPoint
class SelectDetectItemFragment :
    BaseFragment<FragmentSelectDetectItemBinding>(R.layout.fragment_select_detect_item) {

    private var selectDetectionItem: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectDetectionItem = it.getString(ARG_SELECT_ITEM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text = selectDetectionItem.orEmpty()
    }
}