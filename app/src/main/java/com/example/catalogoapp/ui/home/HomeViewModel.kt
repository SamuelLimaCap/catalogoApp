package com.example.catalogoapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val repository: CatalogoRepository
): ViewModel() {
    val listProducts = MutableLiveData<List<ProductEntity>>()

    init {
        getListProducts()
    }

     fun getListProducts() {
        viewModelScope.launch {
            listProducts.postValue(repository.getAllProducts())
        }
    }
}