package com.example.catalogoapp.ui.adapters.export

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.databinding.ItemCategorySelectBinding
import com.example.catalogoapp.model.CategoryEntity

class SelectCategoryToExportAdapter :
    RecyclerView.Adapter<SelectCategoryToExportAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(var binding: ItemCategorySelectBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var items = emptyList<CategoryEntity>()
    fun submitList(list: List<CategoryEntity>) {
        items = list
        notifyDataSetChanged()
    }

    private val selectedItems: MutableList<String> = mutableListOf()
    fun getSelectedItems() = selectedItems.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategorySelectBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            categoryName.text = items[position].category

            checkExport.setOnClickListener {
                if (checkExport.isSelected) {
                    selectedItems.remove(categoryName.text.toString())
                } else {
                    selectedItems.add(categoryName.text.toString())
                }
            }
        }
    }

    override fun getItemCount() = items.size
}