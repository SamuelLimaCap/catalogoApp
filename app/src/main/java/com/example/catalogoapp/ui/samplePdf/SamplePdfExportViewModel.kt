package com.example.catalogoapp.ui.samplePdf

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class SamplePdfExportViewModel(val repository: CatalogoRepository) : ViewModel() {

    val listCategory = MutableLiveData<List<CategoryEntity>>()
    val listProductsByCategory = MutableLiveData<List<ProductsGroupByCategory>>()

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


}