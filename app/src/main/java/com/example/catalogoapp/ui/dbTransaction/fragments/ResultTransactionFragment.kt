package com.example.catalogoapp.ui.dbTransaction.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentResultTransactionBinding

class ResultTransactionFragment : Fragment() {

    lateinit var binding: FragmentResultTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultTransactionBinding.inflate(layoutInflater, container, false)

        val isSuccess = arguments?.let { ResultTransactionFragmentArgs.fromBundle(it).isSucess } ?: false
        if (isSuccess) {
            setSuccessLayout()
        } else {
            setFailedLayout()

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
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setFailedLayout() {
        val redColor = resources.getColor(R.color.red)
        binding.resultText.text = getString(R.string.failed_transaction)
        binding.resultText.setTextColor(redColor)
        binding.backButton.setTextColor(redColor)
        binding.backButton.backgroundTintList = resources.getColorStateList(R.color.red, null)
        binding.backButton.background = ContextCompat.getDrawable(requireActivity(), R.drawable.red_button_border);
    }




}