package com.example.catalogoapp.ui.dbTransaction.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.catalogoapp.data.db.CategoryEntity
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

        binding.addCategoryButton.setOnClickListener {
            val categoryName = binding.categoryNameInput.text.toString()
            if (isValidCategoryName(categoryName)) {
                addCategory(categoryName)

            }
        }

        return binding.root
    }

    private fun addCategory(categoryName: String) {
        GlobalScope.launch {
            val result = viewModel.addCategoryToDB(CategoryEntity(category = categoryName))


        }


    }

    private fun isValidCategoryName(name: String): Boolean {
        if (name.isEmpty() || name.isBlank()) return false
        return true
    }
}