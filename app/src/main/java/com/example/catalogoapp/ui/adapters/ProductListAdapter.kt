package com.example.catalogoapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.ProductItemBinding
import com.example.catalogoapp.model.Product

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ListViewHolder>() {

    private val list: List<Product> = listOf(
        Product(null, "oi",2.45F,"unidade", "teste"),
        Product(null, "oi",2.55F,"unidade", "teste"))

    inner class ListViewHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingInflater = ProductItemBinding.inflate(inflater, parent, false);
        return ListViewHolder(bindingInflater);

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.imageItem.setImageResource(R.drawable.image_item_preview)
        Log.e("Adapter", "entrou")
        holder.binding.nameItem.text = list[position].name
        val price = list[position].price.toString() + "/ " + list[position].unit
        holder.binding.priceItem.text = price

    }

    override fun getItemCount(): Int = list.size



}