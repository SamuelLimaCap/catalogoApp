package com.example.catalogoapp.ui.dbTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoapp.repository.CatalogoRepository

class DbTransactionVMProviderFactory(
    val repository: CatalogoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DbTransactionViewModel(repository) as T
    }
}