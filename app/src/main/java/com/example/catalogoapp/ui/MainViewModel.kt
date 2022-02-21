package com.example.catalogoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class MainViewModel(
    val repository: CatalogoRepository
) : ViewModel() {

    private val productList: MutableLiveData<ProductEntity> = MutableLiveData()

    val listProductsByCategory = MutableLiveData<List<ProductsGroupByCategory>>()
    val listCategory = MutableLiveData<List<CategoryEntity>>()


    fun getCategories() {
        viewModelScope.launch { listCategory.postValue(repository.getAllCategories()); println(listCategory.value) }

    }

    fun getProductsGroupByCategories(listCategory: List<String>) {
        viewModelScope.launch {
            val list: MutableList<ProductsGroupByCategory> = mutableListOf()
            for (category in listCategory) {
                val products = repository.getAllProductsByCategory(category)
                val productsGroupByCategory = ProductsGroupByCategory(category, products)
                list.add(productsGroupByCategory)
            }
            listProductsByCategory.postValue(list.toList())

            return@launch
        }
    }
    fun getProductByCategory(categoryName: String) {
        viewModelScope.launch {
            repository.getAllProductsByCategory(categoryName)
        }
    }

}