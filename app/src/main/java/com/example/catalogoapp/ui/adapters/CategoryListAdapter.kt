package com.example.catalogoapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.databinding.CategoryItemBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionActivity

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    lateinit var binding: CategoryItemBinding
    private var list: List<CategoryEntity> = emptyList()
    inner class CategoryListViewHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.binding.apply {
            categoryName.text = list[position].category
            editButton.setOnClickListener {
                val intent = Intent(holder.itemView.context, DbTransactionActivity::class.java)
                intent.putExtra("type", "edit")
                intent.putExtra("entity", "category")
                intent.putExtra("name", list[position].category)
                holder.itemView.context.startActivity(intent)
            }
            deleteButton.setOnClickListener {
                val intent = Intent(holder.itemView.context, DbTransactionActivity::class.java)
                intent.putExtra("type", "remove")
                intent.putExtra("entity", "category")
                intent.putExtra("name", list[position].category)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<CategoryEntity>) {
        this.list = list
        notifyDataSetChanged()
    }
}
