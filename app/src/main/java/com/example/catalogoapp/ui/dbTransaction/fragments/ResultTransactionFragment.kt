package com.example.catalogoapp.ui.dbTransaction.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentResultTransactionBinding
import com.example.catalogoapp.ui.dbTransaction.DbTransactionViewModel

class ResultTransactionFragment : Fragment() {

    lateinit var binding: FragmentResultTransactionBinding
    val args: ResultTransactionFragmentArgs by navArgs()
    val viewModel: DbTransactionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultTransactionBinding.inflate(layoutInflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Result"
        var errorMoreInfo: Int = R.string.no_description_transaction
        if (viewModel.errorTransactionInfo.value != null) {
            errorMoreInfo = viewModel.errorTransactionInfo.value!!
        }

        viewModel.errorTransactionInfo.observe(viewLifecycleOwner) {
            errorMoreInfo = it
            setFailedLayout(it)
        }

        if (args.isSucess && errorMoreInfo == R.string.no_description_transaction) {
            setSuccessLayout()
        } else {
            setFailedLayout(args.moreInfo)

        }



        binding.backButton.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setSuccessLayout() {
        val greenColor = resources.getColor(R.color.green)
        binding.resultText.text = getString(R.string.successfully_added_transaction)
        binding.resultText.setTextColor(greenColor)
        binding.backButton.setTextColor(greenColor)
        binding.backButton.backgroundTintList = resources.getColorStateList(R.color.green, null)
        binding.backButton.background = ContextCompat.getDrawable(requireActivity(), R.drawable.green_button_border);
        binding.moreInfoText.setText(resources.getText(R.string.no_description_transaction))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setFailedLayout(resourceTextId: Int) {
        val redColor = resources.getColor(R.color.red)
        binding.resultText.text = getString(R.string.failed_transaction)
        binding.resultText.setTextColor(redColor)
        binding.backButton.setTextColor(redColor)
        binding.backButton.backgroundTintList = resources.getColorStateList(R.color.red, null)
        binding.backButton.background = ContextCompat.getDrawable(requireActivity(), R.drawable.red_button_border);
        binding.moreInfoText.setText(resources.getText(resourceTextId))
    }




}