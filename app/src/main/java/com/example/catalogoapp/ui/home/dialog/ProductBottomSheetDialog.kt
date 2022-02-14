package com.example.catalogoapp.ui.home.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catalogoapp.data.db.dao.model.ProductEntity
import com.example.catalogoapp.databinding.BottomSheetDialogBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductBottomSheetDialog(val product: ProductEntity) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDialogBinding.inflate(layoutInflater)

        binding.layoutEditProduct.setOnClickListener {
            val intent = Intent(this.requireContext(), DbTransactionActivity::class.java)
            intent.putExtra("type", "edit")
            intent.putExtra("entity", "product")
            intent.putExtra("id", product.id.toString())
            startActivity(intent)
        }
        binding.layoutDeleteProduct.setOnClickListener {
            val intent = Intent(this.requireContext(), DbTransactionActivity::class.java)
            intent.putExtra("type", "remove")
            intent.putExtra("entity", "product")
            intent.putExtra("name", product.name)
            intent.putExtra("id", product.id.toString())
            startActivity(intent)
        }

        return binding.root
    }




}