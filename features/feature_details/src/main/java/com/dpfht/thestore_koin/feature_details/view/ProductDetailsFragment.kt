package com.dpfht.thestore_koin.feature_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dpfht.thestore_koin.framework.R
import com.dpfht.thestore_koin.feature_details.databinding.FragmentProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

  private lateinit var binding: FragmentProductDetailsBinding
  private var isTablet = false

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    arguments?.let {
      val title = it.getString("title")
      val price = it.getString("price")
      val desc = it.getString("desc")
      val image = it.getString("image")

      isTablet = requireContext().resources.getBoolean(R.bool.isTablet)
      if (isTablet) {
        setToolbar("Order Barang")
      } else {
        setToolbar(title ?: "")
      }

      binding.tvTitle.text = title
      binding.tvPrice.text = price
      binding.tvDesc.text = desc

      if (image?.isNotEmpty() == true) {
        Picasso.get().load(image)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(binding.ivProduct)
      }
    }
  }

  private fun setToolbar(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = title
  }
}
