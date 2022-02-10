package com.example.catalogoapp.ui.export

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.ExportFragmentBinding

class ExportFragment : Fragment() {
    private lateinit var viewModel: ExportViewModel
    private lateinit var binding: ExportFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ExportFragmentBinding.inflate(inflater, container, false);
        viewModel = ViewModelProvider(this).get(ExportViewModel::class.java)
        setActionBar(R.string.title_export)
        return binding.root
    }

    private fun setActionBar(titleResource: Int) {
        activity?.actionBar?.apply {
            setTitle(titleResource)
        }
    }

}