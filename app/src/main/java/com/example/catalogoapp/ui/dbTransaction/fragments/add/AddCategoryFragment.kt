package com.example.catalogoapp.ui.dbTransaction.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.databinding.FragmentAddCategoryBinding
import com.example.catalogoapp.model.Response
import com.example.catalogoapp.model.exception.InvalidInputException
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel


class AddCategoryFragment : Fragment() {
    private lateinit var binding: FragmentAddCategoryBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_add_category)

        setOnClickAddCategoryButton()

        return binding.root
    }

    private fun setOnClickAddCategoryButton() {
        binding.addCategoryButton.setOnClickListener {
            val categoryName = binding.categoryNameInput.text.toString()
            val response = checkInputContent(categoryName)
            var isSuccess = false
            if (response.isSuccess) {
                addCategory(categoryName)
                isSuccess = true
                navigateToTransactionFragment(isSuccess, it)
            } else {
                Toast.makeText(requireContext(), response.reason, Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun addCategory(categoryName: String) {
        viewModel.addCategoryToDB(CategoryEntity(category = categoryName))
    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action = AddCategoryFragmentDirections.actionAddCategoryFragmentToTransactionFragment(
            isSuccess, R.string.no_description_transaction)
        view.findNavController().navigate(action)
    }

    private fun checkInputContent(name: String): Response<Boolean> {
        try {
            val category = CategoryEntity(name)
            val response = viewModel.isCategoryContentValid(category)
            return Response(response, "")
        } catch (e: InvalidInputException) {
            return Response(false, getString(e.resourceIdMessage))
        }
    }
}