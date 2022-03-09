package com.example.catalogoapp.ui.adapters.export

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.databinding.RowProductsGroupByCategoryBinding
import com.example.catalogoapp.model.ProductsGroupByCategory

class ExportProductsGroupByCategoryAdapter :
    RecyclerView.Adapter<ExportProductsGroupByCategoryAdapter.ProductsGroupCategoryViewHolder>() {

    val list: List<ProductsGroupByCategory> = emptyList()
    inner class ProductsGroupCategoryViewHolder(var binding: RowProductsGroupByCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsGroupCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowProductsGroupByCategoryBinding.inflate(layoutInflater, parent, false)
        return ProductsGroupCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsGroupCategoryViewHolder, position: Int) {
        if (position==0) {
            holder.binding.categoryName.text = list[position].category.category
            holder.binding.rvListProduct
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}