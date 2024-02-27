package com.example.testapplication.feature_products_view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.core.pojos.ResponseProduct
import com.example.testapplication.databinding.ItemProductBinding
import com.example.testapplication.feature_products_view.viewHolders.ProductRecyclerViewHolder

class ProductsRecyclerAdapter(
    private val list: List<ResponseProduct>,
    private val clickListener: (data: ResponseProduct) -> Unit
): RecyclerView.Adapter<ProductRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductRecyclerViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProductRecyclerViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }
}