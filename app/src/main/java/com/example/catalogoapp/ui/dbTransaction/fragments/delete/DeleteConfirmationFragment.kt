package com.example.catalogoapp.ui.dbTransaction.fragments.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catalogoapp.R
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.databinding.FragmentDeleteConfirmationBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel

class DeleteConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentDeleteConfirmationBinding
    private val args: DeleteConfirmationFragmentArgs by navArgs()
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteConfirmationBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "delete confirmation"
        binding.sureButton.setOnClickListener {
            var isSuccess = false
            when (args.type) {
                "product" -> {
                    viewModel.deleteProductByIdOnBD(productId = args.id)
                    isSuccess = true
                }
                "category" -> {
                    viewModel.deleteCategory(CategoryEntity(args.name))
                    isSuccess = true
                }
            }
            navigateToTransactionFragment(isSuccess, it)
        }


        binding.deleteItemToDeleteText.text = args.name
        return binding.root
    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            DeleteConfirmationFragmentDirections.actionDeleteConfirmationFragmentToTransactionFragment(
                isSuccess, R.string.no_description_transaction
            )
        view.findNavController().navigate(action)
    }
}