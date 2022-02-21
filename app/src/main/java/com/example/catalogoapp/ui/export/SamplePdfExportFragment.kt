package com.example.catalogoapp.ui.export

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.FragmentExportPdfBinding
import com.example.catalogoapp.ui.MainViewModel
import com.example.catalogoapp.ui.adapters.ProductsGroupByCategoryAdapter
import com.example.catalogoapp.utils.FilesUtil
import java.net.URI

class SamplePdfExportFragment : Fragment() {

    lateinit var binding: FragmentExportPdfBinding
    val viewmodel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExportPdfBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setConfirmButton()

        return binding.root
    }

    private fun setActionBar(titleResource: Int) {
        activity?.actionBar?.apply {
            setTitle(titleResource)
        }
    }

    private fun setConfirmButton() {
        binding.fabConfirmExport.setOnClickListener {
            val uri = exportProductsAsPDF()
            if (uri != null) {
                findNavController().navigate(R.id.exportFragment)
                shareOnWhatsapp(uri)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to export! You need a disk space to export as PDF",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val newRvAdapter = ProductsGroupByCategoryAdapter()
        newRvAdapter.submitList(viewmodel.listProductsByCategory.value!!)
        binding.idToExport.adapter = newRvAdapter
        binding.idToExport.layoutManager = layoutManager
    }

    private fun exportProductsAsPDF() : URI? {
        val uri = FilesUtil.exportAsPDF(requireContext(), "exportedPdf", binding.idToExport)
        val uriExternal = FilesUtil.writeFileExternalStorage(
            uri,
            checkPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )

        return  uriExternal
    }

    val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private fun checkPermission(context: Context, permission: String): Boolean {
        var check = ContextCompat.checkSelfPermission(context, permission)
        if (check == PackageManager.PERMISSION_DENIED) {
            requestPermission.launch(
                permission
            )
            check = ContextCompat.checkSelfPermission(requireContext(), permission)
        }
        return check == PackageManager.PERMISSION_GRANTED
    }

    private fun shareOnWhatsapp(uriExternal: URI) {
        val shareWhatsappIntent = Intent().also {
            it.action = Intent.ACTION_SEND
            it.type = "application/pdf"
            it.putExtra(Intent.EXTRA_STREAM, uriExternal)
        }
        requireActivity().startActivity(shareWhatsappIntent)
    }


}