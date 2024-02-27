package com.example.testapplication.feature_products_view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.core.pojos.NetworkState
import com.example.testapplication.core.pojos.ResponseProduct
import com.example.testapplication.core.utils.gone
import com.example.testapplication.core.utils.visible
import com.example.testapplication.databinding.FragmentProductsBinding
import com.example.testapplication.feature_products_view.adapters.ProductsRecyclerAdapter
import com.example.testapplication.feature_products_view.viewModels.ProductViewModel
import com.tapadoo.alerter.Alerter

class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private val viewModel: ProductViewModel by activityViewModels()
    private val mutableList = mutableListOf<ResponseProduct>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
        initData()
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelServices()
    }

    private fun initRecycler() {
        binding.recyclerProducts.adapter = ProductsRecyclerAdapter(
            mutableList, ::recyclerClickListener
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadProductsData(listProducts: List<ResponseProduct>) {
        if(listProducts.isNotEmpty()) {
            mutableList.clear()
            mutableList.addAll(listProducts)
            binding.recyclerProducts.adapter?.notifyDataSetChanged()
        }
    }

    private fun initObservers() {
        viewModel.responseInfo.observe(viewLifecycleOwner) { response ->
            response?.let { responseProduct -> loadProductsData(responseProduct.listProducts)}
        }
        viewModel.networkState.observe(viewLifecycleOwner) { state ->
            state?.let { stateNotNull ->
                when(stateNotNull) {
                    NetworkState.OK -> binding.viewProgress.progressView.gone()
                    NetworkState.FAIL, NetworkState.ERROR_APP,
                    NetworkState.ERROR_SERVICE, NetworkState.NO_INTERNET -> showErrorAndGoneProgress()
                    NetworkState.LOAD -> binding.viewProgress.progressView.visible()
                }
                binding.swipeRefreshProducts.isRefreshing = false
            }
        }
    }

    private fun showErrorAndGoneProgress() {
        val message = viewModel.responseInfo.value?.message
        Log.i("TAG", "showErrorAndGoneProgress: $message")
        message?.let { text ->
            if (text.isNotEmpty()) Alerter.create(requireActivity())
                .setTitle("Atención")
                .setText(text)
                .setBackgroundColorRes(R.color.colorAccent)
                .setDuration(4000)
                .show()
        }
        binding.viewProgress.progressView.gone()
    }

    private fun initData() {
        viewModel.getProducts()
        binding.swipeRefreshProducts.setOnRefreshListener {
            viewModel.getProducts()
            binding.swipeRefreshProducts.isRefreshing = true
        }
    }

    private fun recyclerClickListener(item: ResponseProduct) {
        val action = ProductsFragmentDirections.actionProductsFragmentToDataProductFragment(item)
        findNavController().navigate(action)
    }
}