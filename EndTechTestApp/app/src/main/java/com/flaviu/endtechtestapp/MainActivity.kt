package com.flaviu.endtechtestapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.flaviu.endtechtestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val adapter = ProductsAdapter(emptyList())
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productsRecyclerView.adapter = adapter

        productsViewModel.productsResult.observe(this, {
            if (it.isSuccess ) {
                // update UI
                adapter.refreshAdapterWithItems(it.products!!)
            } else {
                // show snackbar error
                Log.e("Error", "Issues with fetched data");
            }
        })
    }

    override fun onStart() {
        super.onStart()
        productsViewModel.fetchProducts()
    }
}