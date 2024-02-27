package com.example.testapplication.feature_products_view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentDataProductBinding
import com.squareup.picasso.Picasso

class DataProductFragment : Fragment() {
    private val args: DataProductFragmentArgs by navArgs()
    private lateinit var binding: FragmentDataProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDataProduct()
        initListeners()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = FragmentDataProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun loadDataProduct() {
        with(binding) {
            val data = args.dataProduct
            titleProduct.text = data.title
            descriptionProductData.text = data.description
            priceProductData.text = "Precio: ${data.price}"
            Picasso.get()
                .load(data.thumbnail)
                .placeholder(R.drawable.hide_image)
                .into(imageProduct)
        }
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            val action = DataProductFragmentDirections.actionDataProductFragmentToProductsFragment()
            findNavController().navigate(action)
        }
    }
}