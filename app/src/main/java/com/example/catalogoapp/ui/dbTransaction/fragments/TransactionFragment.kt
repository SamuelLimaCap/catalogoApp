package com.example.catalogoapp.ui.dbTransaction.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    lateinit var binding: FragmentTransactionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)

        binding.backButton.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }



}