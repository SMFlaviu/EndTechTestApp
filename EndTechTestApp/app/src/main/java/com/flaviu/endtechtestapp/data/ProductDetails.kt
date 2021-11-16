package com.flaviu.endtechtestapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetails(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("image") val image: String ="",
) : Parcelable
