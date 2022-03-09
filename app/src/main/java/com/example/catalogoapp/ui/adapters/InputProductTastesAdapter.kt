package com.example.catalogoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.RowInputProductTastesBinding
import com.example.catalogoapp.model.ProductTastes

class InputProductTastesAdapter : RecyclerView.Adapter<InputProductTastesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowInputProductTastesBinding) : RecyclerView.ViewHolder(binding.root)

    private var list = mutableListOf<ProductTastes>()

    private var hasToShowPrices = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowInputProductTastesBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resources = holder.itemView.resources
        val textFieldName = holder.binding.textFieldTasteName
        val textFieldPrice = holder.binding.textFieldTastePrice

        textFieldName.hint = resources.getString(R.string.hint_taste_x)
        if (list[holder.bindingAdapterPosition].taste == null && textFieldName.editText?.text.toString()
                .isNotEmpty()
        ) textFieldName.editText?.setText("")

        textFieldPrice.hint = resources.getString(R.string.hint_price_x)
        if (list[holder.bindingAdapterPosition].price == null && textFieldPrice.editText?.text.toString()
                .isNotEmpty()
        ) textFieldPrice.editText?.setText("")

        holder.binding.ivDeleteTaste.setOnClickListener {
            removeTasteAtPosition(holder.bindingAdapterPosition)
        }

        holder.binding.textFieldTastePrice.visibility = if (!hasToShowPrices) View.GONE else View.VISIBLE

    }

    override fun getItemCount() = list.size


    fun submitList(newList: List<ProductTastes>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun addTaste(taste: ProductTastes) {
        taste.id = list.size.toLong()
        list.add(taste)
        val position = list.indexOf(taste)

        notifyItemInserted(list.size - 1)
    }

    private fun removeTasteAtPosition(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }


}