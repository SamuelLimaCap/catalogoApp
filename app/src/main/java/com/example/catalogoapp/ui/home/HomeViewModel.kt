package com.example.catalogoapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: CatalogoRepository
): ViewModel() {
    val listProductsGroupByCategory = MutableLiveData<List<ProductsGroupByCategory>>()
    val listCategories = MutableLiveData<List<CategoryEntity>>()
    init {
        getListProductsByCategory()
    }

     fun getListProductsByCategory() {
        viewModelScope.launch {
            listProductsGroupByCategory.postValue(repository.getProductsGroupByCategory())
        }
    }

    fun getListCategories() {
        viewModelScope.launch {
            listCategories.postValue(repository.getAllCategories())
        }
    }
}