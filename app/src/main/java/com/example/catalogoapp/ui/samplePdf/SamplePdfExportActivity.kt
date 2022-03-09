package com.example.catalogoapp.ui.samplePdf

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.FragmentExportPdfBinding
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.adapters.ProductsGroupByCategoryAdapter
import com.example.catalogoapp.ui.adapters.export.ExportLayoutAdapter
import com.example.catalogoapp.utils.ExportToPdf
import com.example.catalogoapp.utils.FilesUtil
import com.example.catalogoapp.utils.NotifyingLinearLayoutManager
import com.example.catalogoapp.utils.ProductsGroupByCategoryUtils
import java.io.File
import java.net.URI


class SamplePdfExportActivity : AppCompatActivity() {

    private lateinit var binding: FragmentExportPdfBinding
    private lateinit var viewModel: SamplePdfExportViewModel
    private lateinit var listToExport: MutableList<ProductsGroupByCategory>
    private val newRvAdapter by lazy { ExportLayoutAdapter() }
    private lateinit var layoutManager: NotifyingLinearLayoutManager
    private lateinit var file: File
    private lateinit var exportToPdfDocument: ExportToPdf
    private var isFinished = false;
    private var hasNewPageToExport = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentExportPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories: ArrayList<String> = intent.getSerializableExtra("categoryList") as ArrayList<String>

        init()
        viewModel.listCategory.observe(this){
            viewModel.getProductsGroupByCategories(it)
        }

        setupObserver()
        setupRecyclerView()
        setConfirmButton()

        viewModel.getCategoryList(categories)

    }

    private fun init() {
        val vmFactory = SamplePdfExportVMProviderFactory(CatalogoRepository(AppDatabase(this)))
        viewModel = ViewModelProvider(this, vmFactory).get(SamplePdfExportViewModel::class.java)
        file = File(this.filesDir, "teste.pdf")

        exportToPdfDocument = ExportToPdf(file)
        isFinished = false
        hasNewPageToExport = false

        listToExport = mutableListOf()
        loadListToExport()
    }

    private fun loadListToExport() {
        listToExport.clear()
        viewModel.listProductsByCategory.value?.forEach {
            listToExport.addAll(ProductsGroupByCategoryUtils.transformListIntoExportList(it))
        }
    }

    /*
            Setup Observer
     */
    private fun setupObserver() {
        viewModel.listProductsByCategory.observe(this) {
            loadListToExport()
            setupRecyclerView()
        }
    }
    /*
           Setup recyclerView
     */

    private fun setupRecyclerView() {
        newRvAdapter.submitList(listToExport)
        binding.idToExport.adapter = newRvAdapter

        layoutManager = NotifyingLinearLayoutManager(this)
        setupLayoutManagerCallBack()
        binding.idToExport.layoutManager = layoutManager
    }

    private fun setupLayoutManagerCallBack() {
        layoutManager.mCallback = object : NotifyingLinearLayoutManager.OnLayoutCompleteCallback {
            override fun onLayoutComplete() {
                if (hasNewPageToExport) {
                    exportToPdfDocument.drawOnPdf(binding.idToExport)
                    tryLoadNewContent()
                }
            }
        }
    }

    private fun tryLoadNewContent() {
        val currentItem = exportToPdfDocument.currentPage - 1
        if (currentItem < listToExport.size) {
            //Load new content and notify the listeners(notify the layoutManager.mCallback)
            binding.idToExport.post(Runnable {
                newRvAdapter.listItems = listOf(listToExport[currentItem])
                newRvAdapter.notifyDataSetChanged()
            })
            hasNewPageToExport = true
        } else {
            hasNewPageToExport = false
            isFinished = true
            val uri = exportToPdf()
            binding.fabConfirmExport.text = getString(R.string.label_close)
            binding.fabConfirmExport.gravity = Gravity.CENTER
            binding.fabConfirmExport.setOnClickListener{
                if (uri != null) shareOnWhatsapp(uri)
                finish()
            }


        }
    }

    private fun exportToPdf(): URI? {
        val success = exportToPdfDocument.writeAndClosePDF()
        if (success) {
            Toast.makeText(this, getString(R.string.label_successfully_exported), Toast.LENGTH_LONG).show()
            clearList()
        }

        return FilesUtil.writeFileExternalStorage(exportToPdfDocument.file.toURI(),
            checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun clearList() {
        binding.idToExport.post(Runnable {
            newRvAdapter.listItems = emptyList()
            newRvAdapter.notifyDataSetChanged()
        })
    }
    /*
           setup Confirm Button
     */

    private fun setConfirmButton() {
        binding.fabConfirmExport.setOnClickListener {
            if (isFinished) {
                Toast.makeText(this, getString(R.string.label_already_exported), Toast.LENGTH_LONG).show()
            } else {
                exportProductsAsPDF()
                binding.fabConfirmExport.text = "exporting..."
                binding.fabConfirmExport.gravity = Gravity.CENTER
                binding.fabConfirmExport.setOnClickListener {  }
            }
        }
    }

    private fun exportProductsAsPDF() {
        tryLoadNewContent()
    }

    private fun checkPermission(context: Context, permission: String): Boolean {
        var check = ContextCompat.checkSelfPermission(context, permission)
        if (check == PackageManager.PERMISSION_DENIED) {
            requestPermission.launch(permission)
            check = ContextCompat.checkSelfPermission(this, permission)
        }
        return check == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private fun shareOnWhatsapp(uriExternal: URI) {
        val file = File(uriExternal)
        val uri = FileProvider.getUriForFile(this, "com.example.catalogoapp.fileprovider", file)

        val shareWhatsappIntent = Intent().also {
            it.action = Intent.ACTION_SEND
            it.`package` = "com.whatsapp"
            it.type = "message/rfc822"
            it.putExtra(Intent.EXTRA_STREAM, uri)
        }
        try {
            startActivity(shareWhatsappIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "You don't have whatsapp installed", Toast.LENGTH_LONG).show()
        }
    }

    private fun setActionBar(titleResource: Int) {
        /*
        activity?.actionBar?.apply {
            setTitle(titleResource)
        }

         */
    }


}