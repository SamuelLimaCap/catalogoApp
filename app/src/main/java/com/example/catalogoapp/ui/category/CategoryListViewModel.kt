package com.example.catalogoapp.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class CategoryListViewModel(
    val repository: CatalogoRepository
) : ViewModel() {

    val categoryList = MutableLiveData<List<CategoryEntity>>()

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            categoryList.postValue(repository.getAllCategories())
        }
    }
}