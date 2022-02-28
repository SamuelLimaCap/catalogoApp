package com.example.catalogoapp.ui.samplePdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.repository.CatalogoRepository

class SamplePdfExportVMProviderFactory(val repository: CatalogoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SamplePdfExportViewModel(repository) as T
    }

}