package com.example.catalogoapp.ui.export

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.ExportFragmentBinding
import com.example.catalogoapp.ui.MainViewModel
import com.example.catalogoapp.ui.adapters.export.SelectCategoryToExportAdapter
import com.example.catalogoapp.ui.samplePdf.SamplePdfExportActivity

class ExportFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: ExportFragmentBinding
    lateinit var adapter: SelectCategoryToExportAdapter
    private var waitingForExport = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ExportFragmentBinding.inflate(inflater, container, false);
        setActionBar(R.string.title_export)

        setupRecyclerView()
        setupExportFAButton()

        viewModel.listProductsByCategory.observe(viewLifecycleOwner) {
            if (waitingForExport) {
                waitingForExport = false;
            }
        }
        viewModel.listCategory.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.getCategories()
        return binding.root
    }


    private fun setActionBar(titleResource: Int) {
        activity?.actionBar?.apply {
            setTitle(titleResource)
        }
    }

    private fun setupRecyclerView() {
        adapter = SelectCategoryToExportAdapter()
        binding.rvListCategory.adapter = adapter
        binding.rvListCategory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupExportFAButton() {
        binding.buttonExport.setOnClickListener {
            waitingForExport = true
            val categories = adapter.getSelectedItems()
            val intent = Intent(requireActivity(), SamplePdfExportActivity::class.java)
            val array = ArrayList<String>()
            array.addAll(categories.toTypedArray())
            intent.putExtra("categoryList",  array)
            startActivity(intent)
        }
    }


}