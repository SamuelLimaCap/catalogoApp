package com.example.catalogoapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.ProductEntity
import com.example.catalogoapp.databinding.ProductItemBinding
import com.example.catalogoapp.model.Product
import com.example.catalogoapp.utils.FilesUtil

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ListViewHolder>() {

    private var list: List<ProductEntity> = listOf()

    inner class ListViewHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingInflater = ProductItemBinding.inflate(inflater, parent, false);
        return ListViewHolder(bindingInflater);

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val productEntity = list[position]
        holder.binding.apply {
            nameItem.text = productEntity.name
            val price = productEntity.price.toString() + "/ " + productEntity.unit
            priceItem.text = price
        }
        if (productEntity.imageName.isNotEmpty()) {
            val context = holder.binding.imageItem.context
            val bitmapImage = FilesUtil.getImageBitmapFromCatalogoImages(productEntity.imageName, context)
            holder.binding.imageItem.setImageBitmap(bitmapImage)
        } else {
            holder.binding.imageItem.setImageResource(R.drawable.image_item_preview)
        }

    }

    fun submitList(list: List<ProductEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size



}