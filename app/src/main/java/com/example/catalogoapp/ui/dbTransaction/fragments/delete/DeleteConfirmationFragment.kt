package com.example.catalogoapp.ui.dbTransaction.fragments.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catalogoapp.databinding.FragmentDeleteConfirmationBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel
import com.example.catalogoapp.ui.dbTransaction.fragments.add.AddProductFragmentDirections

class DeleteConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentDeleteConfirmationBinding
    val args: DeleteConfirmationFragmentArgs by navArgs()
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteConfirmationBinding.inflate(inflater, container, false)

        binding.sureButton.setOnClickListener {
            var isSuccess = false
            when (args.type) {
                "product" -> {
                    viewModel.deleteProductByIdOnBD(productId = args.id)
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
            AddProductFragmentDirections.actionAddProductFragmentToTransactionFragment(
                isSuccess
            )
        view.findNavController().navigate(action)
    }
}