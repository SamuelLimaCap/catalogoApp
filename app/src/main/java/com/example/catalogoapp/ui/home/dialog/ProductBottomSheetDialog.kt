package com.example.catalogoapp.ui.home.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catalogoapp.databinding.BottomSheetDialogBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductBottomSheetDialog(val idProduct: Long) : BottomSheetDialogFragment() {

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
            intent.putExtra("id", idProduct.toString())
            startActivity(intent)
        }
        binding.layoutDeleteProduct.setOnClickListener {
            val intent = Intent(this.requireContext(), DbTransactionActivity::class.java)
            intent.putExtra("type", "remove")
            intent.putExtra("entity", "product")
            intent.putExtra("id", idProduct.toString())
            startActivity(intent)
        }

        return binding.root
    }




}