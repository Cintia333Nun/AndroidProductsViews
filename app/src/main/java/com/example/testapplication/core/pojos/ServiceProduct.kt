package com.example.testapplication.core.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseProductGeneric(
    val message: String = "",
    @SerializedName("products") val listProducts: List<ResponseProduct>,
)

@Parcelize
data class ResponseProduct(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String,
): Parcelable

