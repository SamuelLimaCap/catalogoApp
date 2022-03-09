package com.example.catalogoapp.ui.adapters.export

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.RowExportItemLayoutBinding
import com.example.catalogoapp.databinding.RowExportItemLayoutRightBinding
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.utils.FilesUtil

class ExportItemLayoutAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = emptyList<ProductEntity>()
    fun submitList(newList: List<ProductEntity>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.row_export_item_layout -> ExportItemsViewHolder.ItemLeftViewHolder(RowExportItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
            R.layout.row_export_item_layout_right -> ExportItemsViewHolder.ItemRightViewHolder(
                RowExportItemLayoutRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("invalid viewType exception")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExportItemsViewHolder.ItemLeftViewHolder -> holder.bind(items[position])
            is ExportItemsViewHolder.ItemRightViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return return when (position % 2) {
            0 -> R.layout.row_export_item_layout
            1 -> R.layout.row_export_item_layout_right
            else -> R.layout.row_export_item_layout
        }
    }


}

sealed class ExportItemsViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class ItemLeftViewHolder(private val binding: RowExportItemLayoutBinding) : ExportItemsViewHolder(binding) {
        fun bind(product: ProductEntity) {
            binding.tvTitle.text = product.name
            binding.ivImage.setImageBitmap(FilesUtil.getImageBitmapFromCatalogoImages(product.imageName,
                binding.ivImage.context))
        }
    }

    class ItemRightViewHolder(private val binding: RowExportItemLayoutRightBinding) : ExportItemsViewHolder(binding) {
        fun bind(product: ProductEntity) {
            binding.tvTitle.text = product.name
            binding.ivImage.setImageBitmap(FilesUtil.getImageBitmapFromCatalogoImages(product.imageName,
                binding.ivImage.context))
        }
    }
}