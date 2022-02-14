package com.example.catalogoapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoapp.repository.CatalogoRepository

class CategoryListVMProviderFactory(val repository: CatalogoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryListViewModel(repository) as T
    }
}