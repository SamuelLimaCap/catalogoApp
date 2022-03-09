package com.example.catalogoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.databinding.ItemCategoryRvProductItemBinding
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory

class ProductsGroupByCategoryAdapter : RecyclerView.Adapter<ProductsGroupByCategoryAdapter.ViewHolder>() {
    var listItems = listOf<ProductsGroupByCategory>()

    fun submitList(newList: List<ProductsGroupByCategory>) {
        listItems = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemCategoryRvProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val adapter = ProductListAdapter(itemView.context)
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

        binding.root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.submitList(listItems[position].productList)
        holder.binding.categoryName.text = listItems[position].category.category
        val categoryName = holder.binding.categoryName

        val wrapSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        categoryName.measure(wrapSpec, wrapSpec)

        categoryName.width = categoryName.measuredWidth + (categoryName.measuredWidth * 0.4).toInt()
    }

    override fun getItemCount() = listItems.size


}