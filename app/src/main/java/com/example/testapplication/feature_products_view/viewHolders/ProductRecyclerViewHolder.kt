package com.example.testapplication.feature_products_view.viewHolders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.core.pojos.ResponseProduct
import com.example.testapplication.databinding.ItemProductBinding

class ProductRecyclerViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(dataProduct: ResponseProduct, clickListener: (data: ResponseProduct) -> Unit) {
        binding.root.setOnClickListener { clickListener.invoke(dataProduct) }
        binding.nameProductData.text = "Nombre: ${dataProduct.title}"
        binding.descriptionProductData.text = dataProduct.description
        binding.priceProductData.text = "Precio: ${dataProduct.price}"
    }
}