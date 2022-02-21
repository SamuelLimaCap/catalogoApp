package com.example.catalogoapp.ui.export

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.ExportFragmentBinding
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.MainViewModel
import com.example.catalogoapp.ui.adapters.ProductsGroupByCategoryAdapter
import com.example.catalogoapp.ui.adapters.export.SelectCategoryToExportAdapter
import java.io.File
import java.io.FileOutputStream

class ExportFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: ExportFragmentBinding
    lateinit var adapter: SelectCategoryToExportAdapter
    private var waitingForExport = false;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExportFragmentBinding.inflate(inflater, container, false);
        setActionBar(R.string.title_export)

        setupRecyclerView()
        setupExportFAButton()

        viewModel.listProductsByCategory.observe(viewLifecycleOwner) {
            if (waitingForExport) {
                Log.e("ExportFragment", "observerListProdutsByCategory")
                findNavController().navigate(R.id.action_exportFragment_to_exportPdfFragment)
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
            viewModel.getProductsGroupByCategories(categories)
        }
    }

    private fun exportAsPDF(list: List<ProductsGroupByCategory>) {
        //Create and setup recycler view
        val recyclerView = RecyclerView(this.requireContext())
        val params = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        recyclerView.layoutParams = params

        val layoutManager = LinearLayoutManager(requireContext())
        val newRvAdapter = ProductsGroupByCategoryAdapter()
        newRvAdapter.submitList(list)

        recyclerView.adapter = newRvAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.visibility = View.VISIBLE

        //End of "create and setup recycler view"

        val file = File(requireContext().filesDir, "sample.pdf")
        val outputStream = FileOutputStream(file)

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(recyclerView.width, recyclerView.height, 1).create()

        val page = pdfDocument.startPage(pageInfo)

        val content = recyclerView
        content.draw(page.canvas)

        pdfDocument.finishPage(page)

        pdfDocument.writeTo(outputStream)

        pdfDocument.close()



    }



}