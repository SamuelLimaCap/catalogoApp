package com.example.catalogoapp.ui.dbTransaction.fragments.update

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
import com.example.catalogoapp.databinding.FragmentUpdateCategoryBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel


class UpdateCategoryFragment : Fragment() {

    lateinit var binding: FragmentUpdateCategoryBinding
    private val args: UpdateCategoryFragmentArgs by navArgs()
    private val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateCategoryBinding.inflate(layoutInflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update category"
        binding.categoryNameInput.setText(args.name)

        setOnClickUpdateButtonAndNavigateToTransactionFragment()
        return binding.root
    }

    private fun setOnClickUpdateButtonAndNavigateToTransactionFragment() {
        var isSuccess = false
        binding.editCategoryButton.setOnClickListener {
            if (binding.categoryNameInput.text.isNotEmpty()) {
                viewModel.updateCategoryToBD(args.name, binding.categoryNameInput.text.toString())
                isSuccess = true
            }
            navigateToTransactionFragment(isSuccess, it)
        }

    }

    private fun navigateToTransactionFragment(isSuccess: Boolean, view: View) {
        val action =
            UpdateCategoryFragmentDirections.actionUpdateCategoryFragmentToTransactionFragment(
                isSuccess, R.string.no_description_transaction
            )
        view.findNavController().navigate(action)
    }

}