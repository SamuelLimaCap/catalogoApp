package com.example.catalogoapp.ui.dbTransaction.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.ActivityDbTransactionBinding
import com.example.catalogoapp.databinding.FragmentEmptyBinding
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.dbTransaction.DbTransactionVMProviderFactory
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel


class EmptyFragment : Fragment() {
    lateinit var binding: FragmentEmptyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEmptyBinding.inflate(inflater, container, false)

        val type = activity?.intent?.extras?.getString("type") ?: ""
        val entity = activity?.intent?.extras?.getString("entity") ?: ""
        val id = activity?.intent?.extras?.getString("id") ?: "-1L"
        val name = activity?.intent?.extras?.getString("name") ?: ""


        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.db_transaction_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        when (type) {
            "add" -> {
                when (entity) {
                    "product" -> navController.navigate(R.id.action_emptyFragment_to_productTransactionFragment)
                    "category" -> navController.navigate(R.id.action_emptyFragment_to_addCategoryFragment)
                }
            }
            "edit" -> {
                when (entity) {
                    "product" -> {
                        val action = EmptyFragmentDirections.actionEmptyFragmentToUpdateProductFragment(id.toLong())
                        navController.navigate(action)
                    }
                }
            }
            "remove" -> {
                val action = EmptyFragmentDirections.actionEmptyFragmentToDeleteConfirmationFragment(type =entity,id =id.toLong(), name =name)
                navController.navigate(action)
            }
        }

        return binding.root
    }

}