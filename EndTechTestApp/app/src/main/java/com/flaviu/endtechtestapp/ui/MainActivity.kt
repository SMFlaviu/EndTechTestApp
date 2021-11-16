package com.flaviu.endtechtestapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.flaviu.endtechtestapp.ui.adapter.ProductsAdapter
import com.flaviu.endtechtestapp.ui.model.ProductsViewModel
import com.flaviu.endtechtestapp.R
import com.flaviu.endtechtestapp.data.ProductDetails
import com.flaviu.endtechtestapp.databinding.ActivityMainBinding
import com.flaviu.endtechtestapp.util.DataState
import com.flaviu.endtechtestapp.util.DataStateEvent
import com.flaviu.endtechtestapp.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val productsViewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        //For testing
        EspressoIdlingResource.increment()
        val adapter = ProductsAdapter(emptyList())
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productsRecyclerView.adapter = adapter


        productsViewModel.productsState.observe(this, {
            when(it) {
                is DataState.Success<List<ProductDetails>> -> {
                    binding.loadingPanel.visibility = View.GONE
                    adapter.refreshAdapterWithItems(it.data)
                }
                is DataState.Error -> {
                    // show error
                    binding.loadingPanel.visibility = View.GONE
                    Toast.makeText(this, "Error fetching data!",Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    // show loading state
                    binding.loadingPanel.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        productsViewModel.setDataStateEvent(DataStateEvent.GetClothesEvent)
    }
}