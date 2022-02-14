package com.example.catalogoapp.ui.dbTransaction.fragments.update

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catalogoapp.R
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.databinding.FragmentUpdateProductBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.utils.FilesUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UpdateProductFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProductBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    val args: UpdateProductFragmentArgs by navArgs()
    private var atualProductEntity: ProductEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProductBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update product"
        loadOptionsSpinner()
        loadCategoriesSpinner()
        setOnClickToAddImage()
        setOnClickUpdateButtonAndNavigateToTransactionFragment()

        viewModel.gettedProductById.observe(viewLifecycleOwner) {
            atualProductEntity = it
            showContentToInputs(productEntity = it)
        }

        val productId = args.id
        if (productId != -1L) {
            getProductContent(productId)
        }

        return binding.root
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
        val positionCategory = categoriesList.indexOf(productEntity.categoryName)
        binding.apply {
            productNameInput.setText(productEntity.name)
            productPriceInput.setText(productEntity.price.toString())
            groupCategoriesSpinner.setSelection(positionCategory)
            groupOptionsSpinner.setSelection(positionOption)
            imagePreview.setImageBitmap(FilesUtil.getImageBitmapFromCatalogoImages(productEntity.imageName, requireContext()))
        }
    }

    private fun setOnClickUpdateButtonAndNavigateToTransactionFragment() {
        binding.updateProductButton.setOnClickListener {
            val isInputsValid = checkInputContent()
            var isSuccess: Boolean = false
            if (isInputsValid) {
                isSuccess = true
                updateProductToBD()
            }
            navigateToTransactionFragment(isSuccess, it)
        }
    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            UpdateProductFragmentDirections.actionUpdateProductFragmentToTransactionFragment(
                isSuccess, R.string.no_description_transaction
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
        val imageName = atualProductEntity?.imageName ?: ""
        val productPrice = binding.productPriceInput.text.toString().toFloat()
        val categorySelected = binding.groupCategoriesSpinner.selectedItem.toString()
        val optionSelected = binding.groupOptionsSpinner.selectedItem.toString()
        return ProductEntity(
            args.id,
            productName,
            productPrice,
            categorySelected,
            imageName,
            optionSelected
        )
    }

    private fun updateProductToBD() {
        val product = getProductFromInputs()
        FilesUtil.savePhotoToInternalStorage(
            requireContext(),
            product.imageName,
            getBitmapFromDrawable(binding.imagePreview.drawable)
        )
        GlobalScope.launch { viewModel.updateProductOnBD(product) }
    }

    private fun getBitmapFromDrawable(drawable: Drawable) = (drawable as BitmapDrawable).bitmap
}