package com.dpfht.bookstore_koin.feature_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dpfht.bookstore_koin.feature_list.databinding.FragmentErrorMessageDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorMessageDialogFragment: BottomSheetDialogFragment() {

  private lateinit var binding: FragmentErrorMessageDialogBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentErrorMessageDialogBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    isCancelable = false

    arguments?.let {
      val message = it.getString("message")
      binding.tvMessage.text = message
    }

    binding.btnOk.setOnClickListener {
      dismiss()
    }
  }
}
