package com.example.catalogoapp.ui.export

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentExportPdfBinding

class ExportPdfFragment : Fragment() {

    lateinit var binding: FragmentExportPdfBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportPdfBinding.inflate(inflater, container, false)



        return binding.root
    }

    //navController.navigate(R.id.exportFragment)
}