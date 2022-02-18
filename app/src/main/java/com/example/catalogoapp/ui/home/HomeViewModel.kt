package com.example.catalogoapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: CatalogoRepository
): ViewModel() {
    val listProducts = MutableLiveData<List<ProductEntity>>()
    val listCategories = MutableLiveData<List<CategoryEntity>>()
    init {
        getListProducts()
    }

     fun getListProducts() {
        viewModelScope.launch {
            listProducts.postValue(repository.getAllProducts())
        }
    }

    fun getListCategories() {
        viewModelScope.launch {
            listCategories.postValue(repository.getAllCategories())
        }
    }
}