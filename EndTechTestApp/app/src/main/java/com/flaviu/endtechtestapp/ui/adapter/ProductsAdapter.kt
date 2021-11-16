package com.flaviu.endtechtestapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flaviu.endtechtestapp.data.ProductDetails
import com.flaviu.endtechtestapp.databinding.ProductsListBinding
import com.flaviu.endtechtestapp.util.EspressoIdlingResource

class ProductsAdapter(private var productsList: List<ProductDetails>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(private val binding: ProductsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductDetails) {
            binding.apply {
                item = product
                Glide.with(binding.root.context)
                    .load(item!!.image)
                    .into(binding.ivItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ProductsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount() = productsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdapterWithItems(newProductsList: List<ProductDetails>) {
        this.productsList = newProductsList
        notifyDataSetChanged()
        //For testing
        EspressoIdlingResource.decrement()
    }
}