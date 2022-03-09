package com.example.catalogoapp.ui.adapters.export

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.databinding.RowExportLayoutBinding
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.ui.adapters.ProductListAdapter
import com.example.catalogoapp.utils.ProductsGroupByCategoryUtils

class ExportLayoutAdapter : RecyclerView.Adapter<ExportLayoutAdapter.ViewHolder>() {
    var listItems = listOf<ProductsGroupByCategory>()

    fun submitList(newList: List<ProductsGroupByCategory>) {
        listItems = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: RowExportLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val adapter = ExportItemLayoutAdapter()
        fun bind() {
            binding.rvListProduct.layoutManager = LinearLayoutManager(itemView.context)
            binding.rvListProduct.adapter = adapter
        }

        fun submitList(list: List<ProductEntity>) {
            adapter.submitList(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExportLayoutAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowExportLayoutBinding.inflate(inflater)

        binding.root.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExportLayoutAdapter.ViewHolder, position: Int) {
        holder.bind()
        holder.submitList(listItems[position].productList)
        holder.binding.categoryName.text = listItems[position].category.category
        holder.binding.categoryName.background =
            ProductsGroupByCategoryUtils.getBgImageFromColor(listItems[position].category.color,
                holder.itemView.context)
        val categoryName = holder.binding.categoryName

        val wrapSec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        categoryName.measure(wrapSec, wrapSec)

        categoryName.width = categoryName.measuredWidth + (categoryName.measuredWidth * 0.4).toInt()
    }

    override fun getItemCount() = listItems.size

}