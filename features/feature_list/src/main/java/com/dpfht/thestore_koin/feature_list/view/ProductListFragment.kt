package com.dpfht.thestore_koin.feature_list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dpfht.thestore_koin.framework.ext.toRupiahString
import com.dpfht.thestore_koin.feature_list.R
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.feature_list.databinding.FragmentProductListBinding
import com.dpfht.thestore_koin.feature_list.databinding.FragmentProductListLandBinding
import com.dpfht.thestore_koin.feature_list.di.ListModule
import com.dpfht.thestore_koin.framework.navigation.NavigationService
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf

class ProductListFragment : Fragment(), KoinComponent {

  private val navigationService: NavigationService by inject()
  private val prgDialog: AlertDialog by inject { parametersOf(this.context) }
  private val viewModel: ProductListViewModel by viewModel()
  private val adapter: ProductListAdapter by inject()

  private lateinit var ivBanner: ImageView
  private lateinit var swRefresh: SwipeRefreshLayout
  private lateinit var rvProduct: RecyclerView

  private var isTablet = false

  private var vw: View? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    if (ListModule.needLoaded) {
      ListModule.needLoaded = false
      loadKoinModules(ListModule.module)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    isTablet = requireContext().resources.getBoolean(com.dpfht.thestore_koin.framework.R.bool.isTablet)

    if (isTablet) {
      if (vw == null) {
        val binding = FragmentProductListLandBinding.inflate(inflater, container, false)

        ivBanner = binding.ivBanner
        swRefresh = binding.swRefresh
        rvProduct = binding.rvProduct

        vw = binding.root
      }
    } else {
      if (vw == null) {
        val binding = FragmentProductListBinding.inflate(inflater, container, false)

        ivBanner = binding.ivBanner
        swRefresh = binding.swRefresh
        rvProduct = binding.rvProduct

        vw = binding.root
      }
    }

    return vw
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setToolbar()

    adapter.products = viewModel.products

    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL

    rvProduct.layoutManager = layoutManager
    rvProduct.adapter = adapter

    adapter.onClickProductListener = object : ProductListAdapter.OnClickProductListener {
      override fun onClickProduct(position: Int) {
        val product = viewModel.getProduct(position)

        if (isTablet) {
          val navHostFragment =
            childFragmentManager.findFragmentById(R.id.details_nav_container) as NavHostFragment

          navigationService.navigateFromListToDetails(
            product.productName,
            "${product.price.toRupiahString()} / pcs",
            product.description,
            product.images?.large ?: "",
            navHostFragment.navController
          )
        } else {
          navigationService.navigateFromListToDetails(
            product.productName,
            "${product.price.toRupiahString()} / pcs",
            product.description,
            product.images?.large ?: "",
            null
          )
        }
      }
    }

    swRefresh.setOnRefreshListener {
      adapter.products.clear()
      adapter.notifyDataSetChanged()
      viewModel.refresh()
    }

    observeViewModel()

    viewModel.start()
  }

  private fun observeViewModel() {
    viewModel.isShowDialogLoading.observe(viewLifecycleOwner) { isLoading ->
      if (isLoading && !swRefresh.isRefreshing) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
        swRefresh.isRefreshing = false
      }
    }

    viewModel.banner.observe(viewLifecycleOwner) { banner ->
      if (banner.isNotEmpty()) {
        Picasso.get().load(banner)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(ivBanner)
      }
    }

    viewModel.notifyItemInserted.observe(viewLifecycleOwner) { position ->
      if (position > 0) {
        adapter.notifyItemInserted(position)
      }
    }

    viewModel.toastMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
      if (msg.isNotEmpty()) {
        showErrorMessage(msg)
      }
    }
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.text_order_barang)
    (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
  }

  private fun showErrorMessage(message: String) {
    navigationService.navigateFromListToError(message)
  }
}
