package com.example.catalogoapp.ui.dbTransaction.fragments.add

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentAddProductBinding
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.Response
import com.example.catalogoapp.model.exception.InvalidInputException
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.utils.Constants
import com.example.catalogoapp.utils.FilesUtil
import com.example.catalogoapp.utils.ImageResizer
import com.example.catalogoapp.utils.ProductUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_add_product)
        loadCategoriesSpinner()
        loadOptionsSpinner()
        setOnClickToAddImage()
        setOnClickAddButton()

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
            if (spinnerItems.isEmpty()) {
                binding.groupCategoriesSpinner.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.red)
            }

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

    private fun setOnClickAddButton() {
        binding.addProductButton.setOnClickListener {
            val response = checkInputContent()
            var isSuccess = false
            if (response.isSuccess) {
                isSuccess = true
                addProductToBD()
                navigateToTransactionFragment(isSuccess, it)
            } else {
                Toast.makeText(requireContext(), response.reason, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInputContent(): Response<Boolean> {
        val response = try {
            if (binding.groupCategoriesSpinner.selectedItem == null) {
                throw InvalidInputException(R.string.error_add_category_before_add_product)
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

    private fun addProductToBD() {
        val product = getProductFromInputs()
        GlobalScope.launch {
            FilesUtil.savePhotoToInternalStorage(
                requireContext(),
                product.imageName,
                ImageResizer.getBitmapFromDrawable(binding.imagePreview.drawable))
            viewModel.addProductToDB(product)
        }

    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action = AddProductFragmentDirections.actionAddProductFragmentToTransactionFragment(
            isSuccess, R.string.no_description_transaction)
        view.findNavController().navigate(action)
    }


    private fun getProductFromInputs(): ProductEntity {
        val productName = binding.productNameInput.text.toString()
        val imageName = ProductUtil.createImageNamePlusOtherName(productName)
        val productPrice = binding.productPriceInput.text.toString().toFloat()
        val categorySelected = binding.groupCategoriesSpinner.selectedItem.toString()
        val optionSelected = binding.groupOptionsSpinner.selectedItem.toString()
        return ProductEntity(
            0, productName, productPrice, categorySelected, imageName, optionSelected)
    }


}