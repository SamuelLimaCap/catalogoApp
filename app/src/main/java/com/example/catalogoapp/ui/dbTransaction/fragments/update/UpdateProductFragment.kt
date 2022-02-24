package com.example.catalogoapp.ui.dbTransaction.fragments.update

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catalogoapp.R
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.databinding.FragmentUpdateProductBinding
import com.example.catalogoapp.model.Response
import com.example.catalogoapp.model.exception.InvalidInputException
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.utils.Constants
import com.example.catalogoapp.utils.FilesUtil
import com.example.catalogoapp.utils.ImageResizer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class UpdateProductFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProductBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    val args: UpdateProductFragmentArgs by navArgs()
    private var atualProductEntity: ProductEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProductBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_update_product)
        loadOptionsSpinner()
        loadCategoriesSpinner()
        setOnClickToAddImage()
        setOnClickUpdateButtonAndNavigateToTransactionFragment()
        setObservers()
        getProductIfExist()

        return binding.root
    }

    private fun loadOptionsSpinner() {
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.spinner_group_options,
            android.R.layout.simple_spinner_dropdown_item).also { arrayAdapter ->
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
                spinnerItems)
            binding.groupCategoriesSpinner.adapter = arrayAdapter
        }

    }

    private fun setOnClickToAddImage() {
        binding.imagePreview.setOnClickListener {
            takePhoto.launch("image/*")
        }
    }

    private val takePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        var bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
        bitmap = ImageResizer.reduceBitmapSize(bitmap, Constants.MAX_SIZE_IMAGE)
        binding.imagePreview.setImageBitmap(bitmap)
    }

    private fun setOnClickUpdateButtonAndNavigateToTransactionFragment() {
        binding.updateProductButton.setOnClickListener {
            val isInputsValid = checkInputContent()
            var isSuccess: Boolean = false
            if (isInputsValid.isSuccess) {
                isSuccess = true
                updateProductToBD()
                navigateToTransactionFragment(isSuccess, it)
            } else {
                Toast.makeText(requireContext(), isInputsValid.reason, Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            UpdateProductFragmentDirections.actionUpdateProductFragmentToTransactionFragment(
                isSuccess, R.string.no_description_transaction)
        requireView().findNavController().navigate(action)
    }

    private fun checkInputContent(): Response<Boolean> {
        val response = try {
            if (binding.groupCategoriesSpinner.selectedItem == null) {
                throw Exception(getString(R.string.error_add_category_before_add_product))
            }

            val product = getProductFromInputs()
            //Throws Exception
            val isProductValid = viewModel.isProductContentValid(product)
            Response(true, "")
        } catch (e: InvalidInputException) {
            Response(false, getString(e.resourceIdMessage))
        }

        return response
    }

    private fun getProductFromInputs(): ProductEntity {
        val productName = binding.productNameInput.text.toString()
        val imageName = atualProductEntity?.imageName ?: ""
        val productPrice = binding.productPriceInput.text.toString().toFloat()
        val categorySelected = binding.groupCategoriesSpinner.selectedItem.toString()
        val optionSelected = binding.groupOptionsSpinner.selectedItem.toString()
        return ProductEntity(
            args.id, productName, productPrice, categorySelected, imageName, optionSelected)
    }

    private fun getProductIfExist() {
        val productId = args.id
        if (productId != -1L) {
            viewModel.getProductById(productId)
        }
    }

    private fun setObservers() {
        viewModel.gettedProductById.observe(viewLifecycleOwner) {
            atualProductEntity = it
            showContentToInputs(productEntity = it)
        }
    }

    private fun showContentToInputs(productEntity: ProductEntity) {
        val numCategories = binding.groupCategoriesSpinner.adapter.count
        val categoriesList = mutableListOf<String>()
        for (num in 0 until numCategories) {
            categoriesList.add(binding.groupCategoriesSpinner.adapter.getItem(num).toString())
        }

        val numOptions = binding.groupOptionsSpinner.adapter.count
        val optionsList = mutableListOf<String>()
        for (num in 0 until numOptions) {
            optionsList.add(binding.groupOptionsSpinner.adapter.getItem(num).toString())
        }

        val positionOption = optionsList.indexOf(productEntity.unit)
        val positionCategory = categoriesList.indexOf(productEntity.categoryName)
        binding.apply {
            productNameInput.setText(productEntity.name)
            productPriceInput.setText(productEntity.price.toString())
            groupCategoriesSpinner.setSelection(positionCategory)
            groupOptionsSpinner.setSelection(positionOption)
            imagePreview.setImageBitmap(
                FilesUtil.getImageBitmapFromCatalogoImages(
                    productEntity.imageName, requireContext()))
        }
    }

    private fun updateProductToBD() {
        val product = getProductFromInputs()
        FilesUtil.savePhotoToInternalStorage(
            requireContext(),
            product.imageName,
            ImageResizer.getBitmapFromDrawable(binding.imagePreview.drawable))
        GlobalScope.launch { viewModel.updateProductOnBD(product) }
    }
}