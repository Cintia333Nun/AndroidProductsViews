package com.example.testapplication.core.repository

import android.content.Context
import com.example.testapplication.core.api.ProductApi
import com.example.testapplication.core.datasource.ProductsDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    @ApplicationContext private val context: Context, private val apiInterface: ProductApi
) {
    fun getRemoteProductsDataSource(): ProductsDataSource = ProductsDataSource(
        context, apiInterface
    )
}