package com.example.catalogoapp.ui.export

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.catalogoapp.databinding.FragmentExportPdfBinding
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.ui.MainViewModel
import com.example.catalogoapp.ui.adapters.ProductsGroupByCategoryAdapter
import com.example.catalogoapp.utils.FilesUtil
import com.example.catalogoapp.utils.NotifyingLinearLayoutManager
import com.example.catalogoapp.utils.ProductsGroupByCategoryUtils
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.net.URI


class SamplePdfExportFragment : Fragment() {

    private lateinit var binding: FragmentExportPdfBinding
    private val viewmodel: MainViewModel by activityViewModels()
    private val newRvAdapter = ProductsGroupByCategoryAdapter()
    private lateinit var layoutManager: NotifyingLinearLayoutManager
    private lateinit var file: File
    private lateinit var pdfDocument: PdfDocument
    private var count: Int = 0;
    private var isFinished = false;
    private var isRvLoading = false;
    var newList = mutableListOf<ProductsGroupByCategory>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportPdfBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setConfirmButton()

        viewmodel.mapCategoryCountProduct.observe(viewLifecycleOwner) {
            val pageNumber = viewmodel.calculatePageNumbers(it)
            exportProductsAsPDF(pageNumber)
            /*
            val uri = exportProductsAsPDF()
            if (uri != null) {
                findNavController().navigate(R.id.exportFragment)
                shareOnWhatsapp(uri)
            } else {
                Toast.makeText(requireContext(),
                    "Failed to export! You need a disk space to export as PDF",
                    Toast.LENGTH_LONG).show()
            }
             */
        }


        file = File(requireContext().filesDir, "teste.pdf")
        //Clear the file content
        if (file.exists()) PrintWriter(file).close()

        pdfDocument = PdfDocument()

        return binding.root
    }

    private fun setupRecyclerView() {
        val newList = mutableListOf<ProductsGroupByCategory>()
        viewmodel.listProductsByCategory.value?.forEach {
            newList.addAll(ProductsGroupByCategoryUtils.transformListIntoExportList(it))
        }
        newRvAdapter.submitList(newList)
        binding.idToExport.adapter = newRvAdapter
        layoutManager = NotifyingLinearLayoutManager(requireContext())
        layoutManager.mCallback = object : NotifyingLinearLayoutManager.OnLayoutCompleteCallback {
            override fun onLayoutComplete() {
                if (isRvLoading) {
                    count++
                    val pageInfo =
                        PdfDocument.PageInfo.Builder(binding.idToExport.width, binding.idToExport.height, count)
                            .create()
                    val page = pdfDocument.startPage(pageInfo)
                    binding.idToExport.draw(page.canvas)
                    pdfDocument.finishPage(page)
                    loadNewContent()
                }
            }
        }
        binding.idToExport.layoutManager = layoutManager
    }

    private fun setActionBar(titleResource: Int) {
        activity?.actionBar?.apply {
            setTitle(titleResource)
        }
    }

    private fun setConfirmButton() {
        binding.fabConfirmExport.setOnClickListener {
            if (isFinished) {
                val outputStream = FileOutputStream(file)
                pdfDocument.writeTo(outputStream)
                pdfDocument.close()
            } else {
                getContentsAndExportToPdf()
                isFinished = true
            }
        }

    }

    private fun getContentsAndExportToPdf() {
        viewmodel.getMapCountGroupByCategory()
        //Observing the map to initialize the export
    }

    private fun exportProductsAsPDF(pageNumber: Int = 1): URI? {
        val categoriesQuantity = binding.idToExport.adapter?.itemCount
        val uri = exportAsPDF()
        // val uri = FilesUtil.exportAsPDF(requireContext(), "exportedPdf", binding.idToExport, pageNumber)

        return FilesUtil.writeFileExternalStorage(uri,
            checkPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun exportAsPDF(): URI {
        val rView = binding.idToExport

        newList = mutableListOf<ProductsGroupByCategory>()
        viewmodel.listProductsByCategory.value?.forEach {
            newList.addAll(ProductsGroupByCategoryUtils.transformListIntoExportList(it))
        }

        isRvLoading = true
        binding.idToExport.post(Runnable {
            newRvAdapter.listItems = listOf(newList[count])
            newRvAdapter.notifyDataSetChanged()
        })
        return file.toURI()
    }

    private fun loadNewContent() {
        if (count < newList.size) {
            binding.idToExport.post(Runnable {
                newRvAdapter.listItems = listOf(newList[count])
                newRvAdapter.notifyDataSetChanged()
            })
        } else {
            isRvLoading = false
        }
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        var check = ContextCompat.checkSelfPermission(context, permission)
        if (check == PackageManager.PERMISSION_DENIED) {
            requestPermission.launch(permission)
            check = ContextCompat.checkSelfPermission(requireContext(), permission)
        }
        return check == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private fun shareOnWhatsapp(uriExternal: URI) {
        val shareWhatsappIntent = Intent().also {
            it.action = Intent.ACTION_SEND
            it.`package` = "com.whatsapp"
            it.type = "application/pdf"
            it.putExtra(Intent.EXTRA_STREAM, uriExternal)
        }
        try {
            requireActivity().startActivity(shareWhatsappIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "You don't have whatsapp installed", Toast.LENGTH_LONG).show()
        }
    }


}