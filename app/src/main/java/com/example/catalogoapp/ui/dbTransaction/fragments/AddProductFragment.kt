package com.example.catalogoapp.ui.dbTransaction.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.ProductEntity
import com.example.catalogoapp.databinding.FragmentAddProductBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.utils.FilesUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)

        loadCategoriesSpinner()
        loadOptionsSpinner()

        binding.imagePreview.setOnClickListener {
            takePhoto.launch("image/*")
        }
        binding.addProductButton.setOnClickListener {
            val isInputsValid = checkInputContent()

            if (isInputsValid) {
                val product = getProductFromInputs()
                FilesUtil.savePhotoToInternalStorage(
                    requireContext(),
                    product.imageName,
                    (binding.imagePreview.drawable as BitmapDrawable).bitmap
                )
                GlobalScope.launch {
                    viewModel.addProductToDB(product)
                }
                val action =
                    AddProductFragmentDirections.actionAddProductFragmentToTransactionFragment(
                        true
                    )
                it.findNavController().navigate(action)
            } else {
                val action = AddProductFragmentDirections.actionAddProductFragmentToTransactionFragment(
                    false
                )
                it.findNavController().navigate(action)
            }
        }
        return binding.root

    }

    private fun checkInputContent(): Boolean {
        binding.apply {
            if (groupOptionsSpinner.selectedItem.toString().isEmpty()) return false
            if (groupCategoriesSpinner.selectedItem.toString().isEmpty()) return false
            if (productNameInput.text.toString().isEmpty()) return false
            if (productPriceInput.text.toString().toFloat() < 0.0) return false
        }
        return true
    }

    private fun getProductFromInputs(): ProductEntity {
        val productName = binding.productNameInput.text.toString()
        val imageName = "${(0..1000).random()}_${('a'..'z').random()}_${productName}"
        val productPrice = binding.productPriceInput.text.toString().toFloat()
        val categorySelected = binding.groupCategoriesSpinner.selectedItem.toString()
        val optionSelected = binding.groupOptionsSpinner.selectedItem.toString()
        return ProductEntity(
            0,
            productName,
            productPrice,
            categorySelected,
            imageName,
            optionSelected
        )
    }
    private val takePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imagePreview.setImageURI(it)
    }


    private fun loadOptionsSpinner() {
        val applyAdapterOnSpinner = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.spinner_group_options,
            android.R.layout.simple_spinner_dropdown_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.groupOptionsSpinner.adapter = arrayAdapter
        }
    }

    private fun loadCategoriesSpinner() {
        lifecycleScope.launch {
            val spinnerItems = viewModel.listAllCategories().map { it -> it.category }.toList()
                ?: emptyList<String>()
            val arrayAdapter = ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerItems
            )
            binding.groupCategoriesSpinner.adapter = arrayAdapter
        }

    }


}