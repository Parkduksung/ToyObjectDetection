package com.example.oic.ui.dialog

import android.os.Bundle
import android.view.View
import com.example.oic.R
import com.example.oic.base.BaseDialogFragment
import com.example.oic.databinding.DialogChooseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseDialog(
    private val chooseItem: ChooseItem,
    private val cancelable: Boolean = true,
    private val dismissCallback: () -> Unit = {}
) :
    BaseDialogFragment<DialogChooseBinding>(R.layout.dialog_choose) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = cancelable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            item = chooseItem
            tvDismiss.setOnClickListener {
                dismissCallback.invoke()
                dismiss()
            }
            tvCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}