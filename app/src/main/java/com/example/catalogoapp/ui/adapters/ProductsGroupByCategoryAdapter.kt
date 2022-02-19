package com.example.catalogoapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.databinding.ItemCategoryRvProductItemBinding
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory

class ProductsGroupByCategoryAdapter :
    RecyclerView.Adapter<ProductsGroupByCategoryAdapter.ViewHolder>() {
    var listItems = listOf<ProductsGroupByCategory>()
    fun submitList(newList: List<ProductsGroupByCategory>) {
        listItems = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemCategoryRvProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val adapter = ProductListAdapter(itemView.context)
        fun bind() {

            binding.rvListProduct.layoutManager = GridLayoutManager(itemView.context, 2)
            binding.rvListProduct.adapter = adapter

        }

        fun submitList(list: List<ProductEntity>) {
            adapter.submitList(list)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryRvProductItemBinding.inflate(inflater)

        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.submitList(listItems[position].productList)
        holder.binding.categoryName.text = listItems[position].category
    }

    override fun getItemCount() = listItems.size


}