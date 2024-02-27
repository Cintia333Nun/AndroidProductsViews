package com.example.testapplication.feature_products_view.viewModels

import androidx.lifecycle.ViewModel
import com.example.testapplication.core.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(repository: ProductsRepository): ViewModel() {
    private val repositoryRemoteProducts = repository.getRemoteProductsDataSource()
    val responseInfo = repositoryRemoteProducts.responseInfo
    val networkState = repositoryRemoteProducts.networkState

    fun getProducts() = repositoryRemoteProducts.getProducts()

    fun cancelServices() = repositoryRemoteProducts.cancelServiceProducts()
}