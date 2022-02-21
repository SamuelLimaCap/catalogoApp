package com.example.catalogoapp.ui.export

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoapp.repository.CatalogoRepository

class ExportViewModelFactory(val repository: CatalogoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExportViewModel(repository) as T

    }
}