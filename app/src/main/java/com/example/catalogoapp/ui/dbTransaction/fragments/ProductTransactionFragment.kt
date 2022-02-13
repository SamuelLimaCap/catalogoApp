package com.example.catalogoapp.ui.dbTransaction.fragments

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.allViews
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.ProductEntity
import com.example.catalogoapp.databinding.FragmentFormProductBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.utils.FilesUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductTransactionFragment : Fragment() {

    private lateinit var binding: FragmentFormProductBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    private var typeTransaction = arguments?.let { ProductTransactionFragmentArgs.fromBundle(it).typeTransaction } ?: ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormProductBinding.inflate(inflater, container, false)

        loadCategoriesSpinner()
        loadOptionsSpinner()
        setOnClickToAddImage()
        setOnClickAddButtonAndNavigateToTransactionFragment()

        when (typeTransaction) {
            "edit" -> {
                val productId = arguments?.let { ProductTransactionFragmentArgs.fromBundle(it).id }
                    ?: -1L
                if (productId != -1L) {
                    getProductContent(productId)
                }
            }
                else -> typeTransaction = "add"
        }

        viewModel.gettedProductById.observe(viewLifecycleOwner) {
            showContentToInputs(productEntity = it)
        }


        return binding.root

    }

    private fun getProductContent(productId: Long) {
        viewModel.getProductById(productId)
    }
    private fun showContentToInputs(productEntity: ProductEntity) {
        val numCategories = binding.groupCategoriesSpinner.adapter.count
        val categoriesList= mutableListOf<String>()
        for (num in 0 until numCategories) {
            categoriesList.add(binding.groupCategoriesSpinner.adapter.getItem(num).toString())
        }

        val numOptions = binding.groupOptionsSpinner.adapter.count
        val optionsList = mutableListOf<String>()
        for(num in 0 until numOptions) {
            optionsList.add(binding.groupOptionsSpinner.adapter.getItem(num).toString())
        }

        val positionOption = optionsList.indexOf(productEntity.unit)
        val positionCategory = optionsList.indexOf(productEntity.categoryName)
        binding.apply {
            productNameInput.setText(productEntity.name)
            productPriceInput.setText(productEntity.price.toString())
            groupCategoriesSpinner.setSelection(positionOption)
            groupOptionsSpinner.setSelection(positionOption)

        }
    }

    private fun loadOptionsSpinner() {
        ArrayAdapter.createFromResource(
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

    private fun setOnClickToAddImage() {
        binding.imagePreview.setOnClickListener {
            takePhoto.launch("image/*")
        }
    }
    private val takePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imagePreview.setImageURI(it)
    }

    private fun setOnClickAddButtonAndNavigateToTransactionFragment() {
        binding.addProductButton.setOnClickListener {
            val isInputsValid = checkInputContent()
            var isSuccess: Boolean = false
            if (isInputsValid) {
                isSuccess = true
                addProductToBD()
            }
            navigateToTransactionFragment(isSuccess, it)
        }
    }

    private fun addProductToBD() {
        val product = getProductFromInputs()
        FilesUtil.savePhotoToInternalStorage(
            requireContext(),
            product.imageName,
            getBitmapFromDrawable(binding.imagePreview.drawable)
        )
        GlobalScope.launch { viewModel.addProductToDB(product) }
    }

    private fun getBitmapFromDrawable(drawable: Drawable) = (drawable as BitmapDrawable).bitmap

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            ProductTransactionFragmentDirections.actionAddProductFragmentToTransactionFragment(
                isSuccess
            )
        view.findNavController().navigate(action)
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


}