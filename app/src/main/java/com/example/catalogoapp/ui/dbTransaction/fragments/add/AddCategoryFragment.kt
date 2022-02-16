package com.example.catalogoapp.ui.dbTransaction.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.databinding.FragmentAddCategoryBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddCategoryFragment : Fragment() {
    private lateinit var binding: FragmentAddCategoryBinding
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_add_category)
        binding.addCategoryButton.setOnClickListener {
            val categoryName = binding.categoryNameInput.text.toString()
            var isSuccess = false
            if (isValidCategoryName(categoryName)) {
                addCategory(categoryName)
                isSuccess = true
            }
            navigateToTransactionFragment(isSuccess, it )
        }

        return binding.root
    }

    private fun addCategory(categoryName: String) {
        GlobalScope.launch {
            viewModel.addCategoryToDB(CategoryEntity(category = categoryName))
        }
    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            AddCategoryFragmentDirections.actionAddCategoryFragmentToTransactionFragment(
                isSuccess, R.string.no_description_transaction
            )
        view.findNavController().navigate(action)
    }

    private fun isValidCategoryName(name: String): Boolean {
        if (name.isEmpty() || name.isBlank()) return false
        return true
    }
}